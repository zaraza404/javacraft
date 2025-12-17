package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.engine.GameRegistry;
import jogo.engine.RenderIndex;
import jogo.framework.math.Vec3;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.gameobject.object.LightSource;
import jogo.gameobject.object.PickableItem;
import jogo.util.WeaponModel;

import java.util.*;

public class RenderAppState extends BaseAppState {

    private final Node rootNode;
    private final AssetManager assetManager;
    private final GameRegistry registry;
    private final RenderIndex renderIndex;
    private final WorldAppState world;

    private Node gameNode;
    private final Map<GameObject, Spatial> instances = new HashMap<>();
    private final Map<GameObject, Light[]> lights = new HashMap<>();

    public RenderAppState(Node rootNode, AssetManager assetManager, GameRegistry registry, RenderIndex renderIndex, WorldAppState world) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
        this.registry = registry;
        this.renderIndex = renderIndex;
        this.world = world;
    }

    @Override
    protected void initialize(Application app) {
        gameNode = new Node("GameObjects");
        rootNode.attachChild(gameNode);
    }

    @Override
    public void update(float tpf) {
        // Ensure each registered object has a spatial and sync position
        var toRemove = registry.getAllToRemove();

        var current = registry.getAll();
        Set<GameObject> alive = new HashSet<>(current);

        for (GameObject obj : current) {
            Spatial s = instances.get(obj);
            if (s == null) {
                s = obj.getSpatial(assetManager);
                if (s != null) {
                    gameNode.attachChild(s);
                    instances.put(obj, s);
                    renderIndex.register(s, obj);
                    if (obj instanceof NonPlayebleGameCharacter){
                        world.addNonPlayableCharacterControl((NonPlayebleGameCharacter) obj, s);
                    }
                    if (obj instanceof LightSource lightSource){
                        Light[] objLights = new Light[lightSource.getLight().length];
                        for (int i = 0; i < objLights.length; i++){
                            Light light = lightSource.getLight()[i];
                            rootNode.addLight(light);
                            objLights[i] = light;
                        }
                        lights.put(obj, objLights);
                    }
                }
            }

            if (!(obj instanceof GameCharacter)){
                s.setLocalTranslation(obj.getPosition().x, obj.getPosition().y, obj.getPosition().z);
            }
            if (obj instanceof PickableItem){
                if (((PickableItem) obj).isPickedUp()){
                    world.startDeletion(obj);
                }
                Vector3f cameraPos = getApplication().getCamera().getLocation();
                s.lookAt(new Vector3f(cameraPos.x, s.getWorldTranslation().y, cameraPos.z), Vector3f.UNIT_Y);
                Vec3 pos = obj.getPosition();
                s.setLocalTranslation(pos.x,pos.y +((PickableItem) obj).getOffcet(tpf),pos.z);
            }
        }

        for (GameObject obj : toRemove) {
            Spatial s = instances.get(obj);
            if (s!=null){
                if (obj instanceof NonPlayebleGameCharacter){
                    world.removeNonPlayableCharacterControl((NonPlayebleGameCharacter) obj, s);
                }
                s.removeFromParent();
            }
            if (obj instanceof LightSource){
                for (Light l : this.lights.get(obj)){
                    rootNode.removeLight(l);
                }
                lights.remove(obj);
            }

            registry.completeRemove(obj);
        }
        //TODO delete code if turns out it is useless

        // Cleanup: remove spatials for objects no longer in registry
        var it = instances.entrySet().iterator();
        while (it.hasNext()) {
            var e = it.next();
            if (!alive.contains(e.getKey())) {
                Spatial s = e.getValue();
                renderIndex.unregister(s);
                if (s.getParent() != null) s.removeFromParent();
                it.remove();
            }
        }
    }

    private Material colored(ColorRGBA color) {
        Material m = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Diffuse", color.clone());
        m.setColor("Specular", ColorRGBA.White.mult(0.1f));
        m.setFloat("Shininess", 8f);
        return m;
    }

    @Override
    protected void cleanup(Application app) {
        if (gameNode != null) {
            gameNode.removeFromParent();
            gameNode = null;
        }
        instances.clear();
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}
