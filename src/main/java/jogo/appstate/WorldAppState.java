package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.engine.GameRegistry;
import jogo.framework.math.Vec3;
import jogo.gameobject.GameObject;
import jogo.gameobject.GameObjectSpawner;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.gameobject.character.enemygamecharacter.EnemyGameCharacter;
import jogo.gameobject.object.AffectObject;
import jogo.systems.gamesave.GameSave;
import jogo.util.pathfinding.Pathfinding;
import jogo.voxel.LevelMap;
import jogo.voxel.VoxelBlockType;
import jogo.voxel.VoxelWorld;
import jogo.voxel.blocks.DoorBlockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldAppState extends BaseAppState {

    private final Node rootNode;
    private final AssetManager assetManager;
    private final GameRegistry registry;
    private final PhysicsSpace physicsSpace;
    private final Camera cam;
    private final InputAppState input;
    private PlayerAppState playerAppState;

    // world root for easy cleanup
    private Node worldNode;
    private VoxelWorld voxelWorld;
    private com.jme3.math.Vector3f spawnPosition;

    private HashMap<NonPlayebleGameCharacter, BetterCharacterControl> npcControls = new HashMap<>();
    private Pathfinding pathfinding;

    private int dungeonFloor = 0;
    private int dungeonSeed = 2010;

    public WorldAppState(Node rootNode, AssetManager assetManager, GameRegistry registry, PhysicsSpace physicsSpace, Camera cam, InputAppState input) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        this.registry = registry;
        this.physicsSpace = physicsSpace;
        this.cam = cam;
        this.input = input;
        this.pathfinding = new Pathfinding();
    }

    public void loadSaveData(int dungeonSeed, int dungeonFloor){
        this.dungeonSeed = dungeonSeed;
        this.dungeonFloor = dungeonFloor;
    }

    public void registerPlayerAppState(PlayerAppState playerAppState) {
        this.playerAppState = playerAppState;
    }

    @Override
    protected void initialize(Application app) {
        worldNode = new Node("World");
        rootNode.attachChild(worldNode);



        // Voxel world 16x16x16 (reduced size for simplicity)
        voxelWorld = new VoxelWorld(assetManager, 320, 32, 320);
        worldNode.attachChild(voxelWorld.getNode());


        loadLevel(dungeonSeed + dungeonFloor);


        // compute recommended spawn
        spawnPosition = voxelWorld.getRecommendedSpawn();
    }

    public com.jme3.math.Vector3f getRecommendedSpawnPosition() {
        return spawnPosition != null ? spawnPosition.clone() : new com.jme3.math.Vector3f(25.5f, 12f, 25.5f);
    }

    public void addNonPlayableCharacterControl(NonPlayebleGameCharacter npc, Spatial spatial){
        BetterCharacterControl npcControl =  new BetterCharacterControl(0.2f, 0.9f, 80f);
        npcControl.setJumpForce(new Vector3f(0,5f,0));
        npcControl.setGravity(new Vector3f(0, -24f, 0));
        npcControl.setJumpForce(new Vector3f(0, 400f, 0));
        physicsSpace.add(npcControl);
        spatial.addControl(npcControl);
        npcControls.put(npc, npcControl);
        npcControl.warp(npc.getPosition().toVector3f());
    }

    public void removeNonPlayableCharacterControl(NonPlayebleGameCharacter npc, Spatial spatial){
        BetterCharacterControl npcControl = npcControls.get(npc);
        physicsSpace.remove(npcControl);
        spatial.removeControl(npcControl);
        npcControls.remove(npc);
    }

    public VoxelWorld getVoxelWorld() {
        return voxelWorld;
    }

    public ArrayList<Vec3> getPathFromTo(Vec3 from, Vec3 to){
        ArrayList<Vec3> path = pathfinding.BuildPath(from, to, voxelWorld.getWalkable(from));
        ArrayList<Vec3> path_centered = new ArrayList<>();
        for (Vec3 pos:path){
            path_centered.add(pos.addVec3(new Vec3(0.5f,0.1f,0.5f)));
        }
        return path_centered;
    }

    public Vec3 getPlayerPosition(){
        return playerAppState.getPlayer().getPosition();
    }

    @Override
    public void update(float tpf) {
        var current = registry.getAll();

        if (input != null && input.consumeToggleShadingRequested()) {
            voxelWorld.toggleRenderDebug();
        }
        for (GameObject obj : current){
            if (obj instanceof GameCharacter chr){
                chr.update(tpf);
                if (chr instanceof NonPlayebleGameCharacter npc){
                    if (npc.getHealth() <= 0.0f) {
                        registry.startRemove(npc);
                        int dropId = npc.getDropId();
                        if (dropId >= 0){
                            GameObjectSpawner.getInstance().spawnDropItem(npc.getDropId(), dungeonFloor, npc.getPosition());
                        }
                        continue;
                    }

                    BetterCharacterControl control = npcControls.get(npc);

                    if (control == null){
                        continue;
                    }

                    npc.setPosition(new Vec3(control.getSpatial().getWorldTranslation()));
                    if (npc instanceof EnemyGameCharacter) {
                        if (npc.getPosition().getXZDistanceTo(getPlayerPosition()) < 1f) {
                            npc.attack(playerAppState.getPlayer());
                        }
                    }
                    if (npc.getPosition().getXZDistanceTo(getPlayerPosition()) < 8f){

                        if (npc.getPosition().getXZDistanceTo(npc.getTargetPosition()) < 0.5f) {
                            npc.decision(this);
                            npc.onArrivedAtPos();
                            if (npc.getPath().isEmpty()) {
                                npc.decision(this);
                            }
                        } else {
                            control.setWalkDirection(npc.getMovementVec3(tpf).toVector3f());
                            control.setViewDirection(npc.getMovementVec3(tpf).toVector3f());
                        }
                    } else {
                        control.setViewDirection(npc.getPosition().addVec3(playerAppState.getPlayer().getPosition().scaleBy(-1)).toVector3f());
                    }



                }
            } else if (obj instanceof AffectObject affObj) {
                if (affObj.isInAffectRadius(getPlayerPosition())){
                    affObj.affect(playerAppState.getPlayer(), tpf);
                }
            }
        }
    }

    public void removeBlockAt(VoxelWorld.Vector3i cell){
        if (voxelWorld.breakAt(cell.x, cell.y, cell.z)) {
            voxelWorld.rebuildDirtyChunks(physicsSpace);
            playerAppState.refreshPhysics();
        }
    }

    public boolean interactWithBlockAt(VoxelWorld.Vector3i cell){
        VoxelBlockType block = voxelWorld.getPalette().get(voxelWorld.getBlock(cell.x, cell.y, cell.z));
        if (block instanceof DoorBlockType){
            dungeonFloor += 1;
            loadLevel(dungeonSeed + dungeonFloor);
        }
        return block.interact();
    }

    public void loadLevel(int levelSeed){
        for (GameObject obj : registry.getAll()){
            startDeletion(obj);
        }
        LevelMap level = new LevelMap("Floor " + dungeonFloor, levelSeed, dungeonFloor);
        byte[][][] level_block_layout= level.getMapBlockLayout();

        voxelWorld.generateLayers(level_block_layout);
        voxelWorld.buildMeshes();
        voxelWorld.clearAllDirtyFlags();
        voxelWorld.buildPhysics(physicsSpace);
        GameObjectSpawner.getInstance().SpawnObjects(level.getGameObjectsLayout(),dungeonFloor);

        voxelWorld.setRecommendedSpawn(new Vector3f(level.getSpawn()[0] + 0.5f, 4f, level.getSpawn()[1] + 0.5f));
        playerAppState.setSpawnPosition(voxelWorld.getRecommendedSpawn());
        playerAppState.moveToSpawn();

        saveGame();

    }

    public void saveGame(){
        if (playerAppState.getPlayer() == null || playerAppState.getInventory() == null) return;
        new GameSave().saveGame(dungeonSeed, dungeonFloor, playerAppState.getPlayer().getHealth(), playerAppState.getInventory().getEquipmentItems(), playerAppState.getInventory().getInventoryItems());
    }



    public int getDungeonFloor(){
        return dungeonFloor;
    }

    @Override
    protected void cleanup(Application app) {
        if (worldNode != null) {
            // Remove all physics controls under worldNode
            worldNode.depthFirstTraversal(spatial -> {
                RigidBodyControl rbc = spatial.getControl(RigidBodyControl.class);
                if (rbc != null) {
                    physicsSpace.remove(rbc);
                    spatial.removeControl(rbc);
                }
            });
            worldNode.removeFromParent();
            worldNode = null;
        }
    }

    public void startDeletion(GameObject obj){
        registry.startRemove(obj);
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}
