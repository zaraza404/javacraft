package jogo.gameobject;

import com.jme3.math.Vector3f;
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

    /*public Spatial getSpatial(){
        Quad quad = new Quad(10,10);
        Geometry obj_visual = new Geometry(getName(), quad);
        obj_visual.setMaterial(new Material().setTexture(assetManager.load_texture("Textures/CharacterBanners/gabe.png"));
        return obj_visual;
    }*/
}
