package jogo.gameobject.character;

import com.jme3.math.Vector3f;
import jogo.framework.math.Vec3;

public class NonPlayebleCharacter extends Character{
    private float speed = 0.1f;
    private Vec3 target_positon = new Vec3(0,2,20);

    public NonPlayebleCharacter() { super("npc");}

    public void move_to_vec(Vec3 new_target_position){
        this.target_positon = new_target_position;
    }

    public void update(float tpf){
        if (position.distance_to(target_positon) > 0.1d){
            Vec3 dir = position.vector_to(target_positon).normalized();
            dir.scale_by(speed * tpf);
            position.add_vec3(dir);
        }
    }
}
