package jogo.gameobject.character.peacefulgamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.framework.math.Vec3;
import jogo.gameobject.GameObjectSpawner;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.gameobject.object.InteractableObject;
import jogo.systems.StatType;

import java.util.ArrayList;
import java.util.Random;

public class Imp extends NonPlayebleGameCharacter implements InteractableObject {
    int[] drops = new int[]{3,10,14,18};
    public Imp() {
        super("Imp");
        setBaseStat(StatType.MOVEMENTSPEED, 0f);
        loadStats();
    }

    @Override
    public void decision(WorldAppState world) {

        setBaseStat(StatType.MOVEMENTSPEED, 0f);
        loadStats();
        Random rand = new Random();
        this.setPath(world.getPathFromTo(position,this.position.addVec3(new Vec3(rand.nextInt(-2,2), position.y, rand.nextInt(-2,2)))));

    }


    @Override
    public boolean interact(WorldAppState world) {
        ArrayList<Vec3> walkable = world.getVoxelWorld().getWalkable(this.position);
        this.setBaseStat(StatType.MOVEMENTSPEED, 10f);
        loadStats();
        this.target_position = walkable.get(walkable.size()-1);
        if (new Random().nextInt()%4 == 0){
            GameObjectSpawner.getInstance().spawnDropItem(drops[new Random().nextInt(4)], world.getDungeonFloor()+2, getPosition());
            world.startDeletion(this);
        }
        return true;
    }


    @Override
    public Spatial getSpatial(AssetManager assetManager){

        Node node = new Node();
        Spatial model = assetManager.loadModel("Models/imp.glb");
        model.scale(0.5f);
        model.setLocalRotation(new Quaternion().fromAngleAxis((float)(-Math.PI/2), Vector3f.UNIT_Y));

        node.attachChild(model);

        return node;

    }
}
