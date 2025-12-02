package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.engine.GameRegistry;
import jogo.framework.math.Vec3;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.util.pathfinding.Pathfinding;
import jogo.voxel.VoxelWorld;

import java.util.ArrayList;
import java.util.HashMap;

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

    public WorldAppState(Node rootNode, AssetManager assetManager, GameRegistry registry, PhysicsSpace physicsSpace, Camera cam, InputAppState input) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        this.registry = registry;
        this.physicsSpace = physicsSpace;
        this.cam = cam;
        this.input = input;
        this.pathfinding = new Pathfinding();
    }

    public void registerPlayerAppState(PlayerAppState playerAppState) {
        this.playerAppState = playerAppState;
    }

    @Override
    protected void initialize(Application app) {
        worldNode = new Node("World");
        rootNode.attachChild(worldNode);

        // Lighting
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.20f)); // slightly increased ambient
        worldNode.addLight(ambient);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.35f, -1.3f, -0.25f).normalizeLocal()); // more top-down to reduce harsh contrast
        sun.setColor(ColorRGBA.White.mult(0.85f)); // slightly dimmer sun
        worldNode.addLight(sun);

        // Voxel world 16x16x16 (reduced size for simplicity)
        voxelWorld = new VoxelWorld(assetManager, 320, 32, 320);
        voxelWorld.generateLayers();
        voxelWorld.buildMeshes();
        voxelWorld.clearAllDirtyFlags();
        worldNode.attachChild(voxelWorld.getNode());
        voxelWorld.buildPhysics(physicsSpace);

        // compute recommended spawn
        spawnPosition = voxelWorld.getRecommendedSpawn();
    }

    public com.jme3.math.Vector3f getRecommendedSpawnPosition() {
        return spawnPosition != null ? spawnPosition.clone() : new com.jme3.math.Vector3f(25.5f, 12f, 25.5f);
    }

    public void addNonPlayableCharacterControl(NonPlayebleGameCharacter npc, Spatial spatial){
        BetterCharacterControl npcControl =  new BetterCharacterControl(0.42f, 1.8f, 80f);

        npcControl.setJumpForce(new Vector3f(0,5f,0));
        npcControl.setGravity(new Vector3f(0, -24f, 0));
        npcControl.setJumpForce(new Vector3f(0, 400f, 0));
        physicsSpace.add(npcControl);
        spatial.addControl(npcControl);
        npcControls.put(npc, npcControl);
        npcControl.warp(npc.getPosition().toVector3f());
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

        if (input != null && input.isMouseCaptured() && input.consumeBreakRequested()) {
            var pick = voxelWorld.pickFirstSolid(cam, 6f);
            pick.ifPresent(hit -> {
                VoxelWorld.Vector3i cell = hit.cell;
                if (voxelWorld.breakAt(cell.x, cell.y, cell.z)) {
                    voxelWorld.rebuildDirtyChunks(physicsSpace);
                    playerAppState.refreshPhysics();
                }
            });
        }
        if (input != null && input.consumeToggleShadingRequested()) {
            voxelWorld.toggleRenderDebug();
        }
        for (GameObject obj : current){
            if (obj instanceof GameCharacter chr){
                chr.update(tpf);
                if (chr instanceof NonPlayebleGameCharacter npc){
                    if (npc.getHealth() <= 0.0f) {
                        registry.remove(npc);
                        continue;
                    }

                    BetterCharacterControl control = npcControls.get(npc);

                    if (control == null){
                        continue;
                    }

                    npc.setPosition(new Vec3(control.getSpatial().getWorldTranslation()));

                    if (npc.getPosition().getXZDistanceTo(getPlayerPosition()) < 1f){
                        npc.attack(playerAppState.getPlayer());
                    }

                    if (npc.getPosition().getXZDistanceTo(npc.getTargetPosition()) < 0.2f) {//returns the same value all the time
                        control.setWalkDirection(Vector3f.ZERO);
                        npc.decision(this);
                        control.setWalkDirection(npc.getMovementVec3(tpf).toVector3f());
                        npc.onArrivedAtPos();
                        if (npc.getPath().isEmpty()) {
                            npc.decision(this);
                        }
                    } else {
                        control.setWalkDirection(npc.getMovementVec3(tpf).toVector3f());
                        control.setViewDirection(npc.getMovementVec3(tpf).toVector3f());
                    }
                }
            }
        }
        for (NonPlayebleGameCharacter npc : npcControls.keySet() ){
        }
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

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}
