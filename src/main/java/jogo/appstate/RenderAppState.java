package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import jogo.engine.GameRegistry;
import jogo.engine.RenderIndex;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.gameobject.character.Player;
import jogo.gameobject.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RenderAppState extends BaseAppState {

    private final Node rootNode;
    private final AssetManager assetManager;
    private final GameRegistry registry;
    private final RenderIndex renderIndex;
    private final WorldAppState world;

    private Node gameNode;
    private final Map<GameObject, Spatial> instances = new HashMap<>();

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

        for (GameObject obj : toRemove) {
            Spatial s = instances.get(obj);
            if (s!=null){
                world.removeNonPlayableCharacterControl((NonPlayebleGameCharacter) obj, s);
                s.removeFromParent();
            }
            registry.completeRemove(obj);
        }
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
                }
            }
            if (!(obj instanceof GameCharacter)){
                s.setLocalTranslation(obj.getPosition().x, obj.getPosition().y, obj.getPosition().z);
            }
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
