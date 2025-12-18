package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.PointLight;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import jogo.framework.math.Vec3;
import jogo.gameobject.GameObjectSpawner;
import jogo.gameobject.character.GameCharacter;
import jogo.systems.gamesave.GameSave;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.InventoryItemRegistry;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;
import jogo.systems.inventory.Inventory;
import jogo.gameobject.character.Player;
import jogo.systems.inventoryitem.consumableitem.food.BerriesItem;
import jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem;
import jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem;
import jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem;
import jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem;
import jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem;
import jogo.util.WeaponModel;

public class PlayerAppState extends BaseAppState {

    private final Node rootNode;
    private final AssetManager assetManager;
    private final Camera cam;
    private final InputAppState input;
    private final PhysicsSpace physicsSpace;
    private final WorldAppState world;

    private Node playerNode;
    private BetterCharacterControl characterControl;
    private Player player;


    private Inventory inventory;

    // view angles
    private float yaw = 0f;
    private float pitch = 0f;

    // tuning
    private float sprintMultiplier = 1.7f;
    private float mouseSensitivity = 30f; // degrees per mouse analog unit
    private float eyeHeight = 0.6f;

    private float savedHealth = -1.0f;

    private Node weaponNode;

    private Vector3f spawnPosition = new Vector3f(25.5f, 12f, 25.5f);
    private PointLight playerLight;

    public PlayerAppState(Node rootNode, AssetManager assetManager, Camera cam, InputAppState input, PhysicsSpace physicsSpace, WorldAppState world) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        this.cam = cam;
        this.input = input;
        this.physicsSpace = physicsSpace;
        this.world = world;
        inventory = new Inventory(12);
        world.registerPlayerAppState(this);
    }

    public void loadSaveData(float playerHealth, int[][] equipmentItems, int[][] inventoryItems){
        for (int i = 0; i < equipmentItems.length; i++){
            int[] itemData = equipmentItems[i];
            if (itemData[0] == -1){
               continue;
            }
            InventoryItem item = InventoryItemRegistry.defaultPalette().get(itemData[0],itemData[1]);
            inventory.addItem(item);
            inventory.equipItem(0,i);
        }

        for (int i = 0; i < inventoryItems.length; i++){
            int[] itemData = inventoryItems[i];
            if (itemData[0] == -1){
                continue;
            }
            InventoryItem item = InventoryItemRegistry.defaultPalette().get(itemData[0],itemData[1]);
            inventory.addItem(item, i);
        }

        savedHealth = playerHealth;
    }

    @Override
    protected void initialize(Application app) {
        // query world for recommended spawn now that it should be initialized
        if (world != null) {
            spawnPosition = world.getRecommendedSpawnPosition();
        }

        playerNode = new Node("Player");
        rootNode.attachChild(playerNode);

        // Engine-neutral player entity (no engine visuals here)
        player = new Player();
        if (savedHealth != -1.0){
            player.setHealth(savedHealth);
        }

        // BetterCharacterControl(radius, height, mass)
        characterControl = new BetterCharacterControl(0.4f, 0.9f, 40f);
        characterControl.setGravity(new Vector3f(0, -24f, 0));
        characterControl.setJumpForce(new Vector3f(0, 120f, 0));
        playerNode.addControl(characterControl);
        physicsSpace.add(characterControl);

        // Local light source that follows the player's head
        playerLight = new PointLight();
        playerLight.setColor(new com.jme3.math.ColorRGBA(0.6f, 0.55f, 0.3f, 1f));
        playerLight.setRadius(8f);
        rootNode.addLight(playerLight);

        // Spawn at recommended location
        respawn();



        // initialize camera
        cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.05f, 500f);
        // Look slightly downward so ground is visible immediately
        this.pitch = -0.35f;
        applyViewToCamera();

        WeaponModel weaponModel = new WeaponModel(assetManager);
        weaponNode = new Node();
        weaponModel.setLocalTranslation(-0.3f,-0.3f,0.4f);
        weaponModel.setLocalRotation(new Quaternion().fromAngleAxis((float)Math.PI/2, Vector3f.UNIT_Y));
        weaponNode.attachChild(weaponModel);
        weaponNode.setLocalTranslation(0,eyeHeight,0);
        playerNode.attachChild(weaponNode);

        player.setWeapon(weaponModel);

        applyViewToWeapon();

    }

    @Override
    public void update(float tpf) {
        // update player class
        player.update(tpf);
        applyViewToWeapon();

        // respawn on request
        if (input.consumeRespawnRequested()) {
            // refresh spawn from world in case terrain changed
            //respawn();
        }

        // pause controls if mouse not captured
        if (!input.isMouseCaptured()) {
            characterControl.setWalkDirection(Vector3f.ZERO);
            // keep light with player even when paused
            if (playerLight != null) playerLight.setPosition(playerNode.getWorldTranslation().add(0, eyeHeight, 0));
            applyViewToCamera();
            return;
        }

        // handle mouse look
        Vector2f md = input.consumeMouseDelta();
        if (md.lengthSquared() != 0f) {
            float degX = md.x * mouseSensitivity;
            float degY = md.y * mouseSensitivity;
            yaw -= degX * FastMath.DEG_TO_RAD;
            pitch -= degY * FastMath.DEG_TO_RAD;
            pitch = FastMath.clamp(pitch, -FastMath.HALF_PI * 0.99f, FastMath.HALF_PI * 0.99f);
        }

        // movement input in XZ plane based on camera yaw
        Vector3f wish = input.getMovementXZ();
        Vector3f dir = Vector3f.ZERO;
        if (wish.lengthSquared() > 0f) {
            dir = computeWorldMove(wish).normalizeLocal();
        }
        float speed = player.getMovementSpeed() * (input.isSprinting() ? sprintMultiplier : 1f);
        characterControl.setWalkDirection(dir.mult(speed));

        // jump
        if (input.consumeJumpRequested() && characterControl.isOnGround()) {
            characterControl.jump();
        }

        player.setPosition(new Vec3(playerNode.getWorldTranslation()));


        // place camera at eye height above physics location
        applyViewToCamera();

        // update light to follow head
        if (playerLight != null) playerLight.setPosition(playerNode.getWorldTranslation().add(0, eyeHeight, 0));

        if (player.getHealth() <= 0){
            new GameSave().deleteSave();
        }
    }

    private void respawn() {
        characterControl.setWalkDirection(Vector3f.ZERO);
        characterControl.warp(spawnPosition);
        System.out.println(spawnPosition);
        // Reset look
        this.pitch = -0.35f;
        applyViewToCamera();
    }

    public int getFloor(){
        return world.getDungeonFloor();
    }

    public void moveToSpawn() {
        if (this.characterControl == null){return;}
        characterControl.setWalkDirection(Vector3f.ZERO);
        characterControl.warp(spawnPosition);
        // Reset look
        this.pitch = -0.35f;
        applyViewToCamera();
    }

    public void setSpawnPosition(Vector3f spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    private Vector3f computeWorldMove(Vector3f inputXZ) {
        // Build forward and left unit vectors from yaw
        float sinY = FastMath.sin(yaw);
        float cosY = FastMath.cos(yaw);
        Vector3f forward = new Vector3f(-sinY, 0, -cosY); // -Z when yaw=0
        Vector3f left = new Vector3f(-cosY, 0, sinY);     // -X when yaw=0
        return left.mult(inputXZ.x).addLocal(forward.mult(inputXZ.z));
    }

    private void applyViewToCamera() {
        // Character world location (spatial is synced by control)
        Vector3f loc = playerNode.getWorldTranslation().add(0, eyeHeight, 0);
        cam.setLocation(loc);
        cam.setRotation(new com.jme3.math.Quaternion().fromAngles(pitch, yaw, 0f));
    }

    private void applyViewToWeapon() {
        weaponNode.setLocalRotation(cam.getRotation());
    }

    public void updateStats() {
        player.setBonusStats(inventory.getStats());
        player.setWeaponModel(inventory.getWeaponModel());
    }

    public void useItem(ConsumableItem item, GameCharacter target){
        inventory.useItem(item, target);
    }
    public void dropItem(InventoryItem item){
        if (item == null){
            return;
        }
        GameObjectSpawner.getInstance().spawnDropItem(InventoryItemRegistry.defaultPalette().getItemId(item), item.getLevel(), player.getPosition());
    }

    @Override
    protected void cleanup(Application app) {
        if (playerNode != null) {
            if (characterControl != null) {
                physicsSpace.remove(characterControl);
                playerNode.removeControl(characterControl);
                characterControl = null;
            }
            playerNode.removeFromParent();
            playerNode = null;
        }
        if (playerLight != null) {
            rootNode.removeLight(playerLight);
            playerLight = null;
        }
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }

    public void refreshPhysics() {
        if (characterControl != null) {
            physicsSpace.remove(characterControl);
            physicsSpace.add(characterControl);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
