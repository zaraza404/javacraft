package jogo.gameobject.character;

import jogo.appstate.WorldAppState;
import jogo.framework.math.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class NonPlayebleGameCharacter extends GameCharacter {
    protected Vec3 target_position = position;
    protected ArrayList<Vec3> path;
    protected int[] dropTable = new int[]{0};

    public NonPlayebleGameCharacter(String name){
        super(name);
        path = new ArrayList<>();
    }

    public void moveToVec3(Vec3 new_target_position){
        this.target_position = new_target_position;
    }

    public Vec3 getMovementVec3(float tpf){
        Vec3 xz_vec = position.getVectorTo(target_position);
        xz_vec.set(xz_vec.x, 0, xz_vec.z);
        Vec3 dir = xz_vec.getNormalized();
        dir = dir.scaleBy(this.getMovementSpeed());
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

    public void setDropItemsTable(int[] dropTable){
        this.dropTable = dropTable;
    }

    public int getDropId(){
        return dropTable[new Random().nextInt(dropTable.length)];
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
