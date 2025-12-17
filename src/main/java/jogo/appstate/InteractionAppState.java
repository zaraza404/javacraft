package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.collision.CollisionResults;
import jogo.engine.RenderIndex;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.enemygamecharacter.EnemyGameCharacter;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.object.InteractableObject;
import jogo.gameobject.object.PickableItem;
import jogo.voxel.VoxelWorld;

public class InteractionAppState extends BaseAppState {

    private final Node rootNode;
    private final Camera cam;
    private final InputAppState input;
    private final RenderIndex renderIndex;
    private final WorldAppState world;
    private final PlayerAppState player;
    private float reach = 1.5f;

    public InteractionAppState(Node rootNode, Camera cam, InputAppState input, RenderIndex renderIndex, WorldAppState world, PlayerAppState player) {
        this.rootNode = rootNode;
        this.cam = cam;
        this.input = input;
        this.renderIndex = renderIndex;
        this.world = world;
        this.player = player;
    }

    @Override
    protected void initialize(Application app) { }

    @Override
    public void update(float tpf) {

        if (input.isMouseCaptured() && input.consumeInteractRequested()){
            Vector3f origin = cam.getLocation();
            Vector3f dir = cam.getDirection().normalize();

            // 1) Try to interact with a rendered GameObject (items)
            Ray ray = new Ray(origin, dir);
            ray.setLimit(reach);
            CollisionResults results = new CollisionResults();
            rootNode.collideWith(ray, results);
            if (results.size() > 0) {
                Spatial hit = results.getClosestCollision().getGeometry();
                GameObject obj = findRegistered(hit);
                if (obj instanceof PickableItem pickableItem) {
                    if (player.getInventory().addItem(pickableItem.pickUp())){
                        System.out.println("Picked up an Item: " + obj.getName());
                        ((PickableItem) obj).setPickedUp();
                    }
                    return;
                } else if ((obj instanceof InteractableObject interactable)){
                    if (interactable.interact()){
                        System.out.println("Interacted with GameObject " + obj.getName());
                        if (interactable.isDeletedOnInteract()){
                            world.startDeletion(obj);
                        }
                    }
                }
            }

            VoxelWorld vw = world != null ? world.getVoxelWorld() : null;
            if (vw != null) {
                vw.pickFirstSolid(cam, reach).ifPresent(hit -> {
                    VoxelWorld.Vector3i cell = hit.cell;
                    world.interactWithBlockAt(cell);
                });
            }
        }

        if (input.isMouseCaptured() && input.consumeBreakRequested()) {
            Vector3f origin = cam.getLocation();
            Vector3f dir = cam.getDirection().normalize();

            Ray ray = new Ray(origin, dir);
            ray.setLimit(reach);
            CollisionResults results = new CollisionResults();
            rootNode.collideWith(ray, results);
            if (results.size() > 0) {
                Spatial hit = results.getClosestCollision().getGeometry();
                GameObject obj = findRegistered(hit);
                if (obj instanceof EnemyGameCharacter character) {
                    System.out.println("Attacked EnemyCharacter: " + obj.getName());
                    player.getPlayer().attack((GameCharacter) obj);
                    return;
                }
            }

            VoxelWorld vw = world != null ? world.getVoxelWorld() : null;
            if (vw != null) {
                vw.pickFirstSolid(cam, reach).ifPresent(hit -> {
                    VoxelWorld.Vector3i cell = hit.cell;
                    world.removeBlockAt(cell);
                });
            }
        }


    }

    private GameObject findRegistered(Spatial s) {
        Spatial cur = s;
        while (cur != null) {
            GameObject obj = renderIndex.lookup(cur);
            if (obj != null) return obj;
            cur = cur.getParent();
        }
        return null;
    }

    @Override
    protected void cleanup(Application app) { }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}
