package jogo.gameobject;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;
import jogo.framework.math.Vec3;

/**
 * Engine-neutral base for all game objects used by students.
 * Stores only identity and logical position; rendering/physics is handled by engine AppStates.
 */
public abstract class GameObject {
    protected final String name;
    protected Vec3 position = new Vec3(new Vector3f(0, 0, 0));

    protected GameObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 pos) {
        this.position.set(pos);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public Spatial getSpatial(AssetManager assetManager){
        Geometry g = new Geometry(getName(), new Cylinder(16, 16, 0.35f, 1.4f, true));
        g.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        return g;
    }
}
