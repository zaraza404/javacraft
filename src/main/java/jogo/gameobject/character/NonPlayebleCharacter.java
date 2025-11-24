package jogo.gameobject.character;

import com.jme3.math.Vector3f;
import jogo.appstate.WorldAppState;
import jogo.framework.math.Vec3;
import jogo.voxel.VoxelWorld;

import java.util.ArrayList;

public abstract class NonPlayebleCharacter extends Character{
    protected float speed = 100.0f;
    protected Vec3 target_position = position;
    protected ArrayList<Vec3> path;


    public NonPlayebleCharacter(String name) {
        super(name);
        path = new ArrayList<>();
    }

    public void move_to_vec3(Vec3 new_target_position){
        this.target_position = new_target_position;
    }

    public Vec3 get_movement_vec3(float tpf){
        Vec3 xz_vec = position.vectorTo(target_position);
        xz_vec.set(xz_vec.x, 0, xz_vec.z);
        Vec3 dir = xz_vec.normalized();
        dir = dir.scaleBy(speed * tpf);
        return dir;
    }

    public Vec3 getTargetPosition(){
        return target_position;
    }

    protected void setPath(ArrayList<Vec3> path){
        this.path = path;

        if (!this.path.isEmpty()){
            this.target_position = path.remove(0);
        }


    }

    public void onArrivedAtPos(){
        if (!path.isEmpty()){
            target_position=path.remove(0);
        }
    }

    public abstract void decision(WorldAppState world);

    public ArrayList<Vec3> getPath() {
        return path;
    }
}
