package jogo.gameobject.character.peacefulgamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.framework.math.Vec3;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.systems.StatType;
import jogo.util.WeaponModel;

import java.util.Random;

public class Chicken extends NonPlayebleGameCharacter {

    public Chicken(){
        super("Chicken");
        this.dropTable = new int[]{2};
        setBaseStat(StatType.MOVEMENTSPEED, 0.5f);
        setBaseStat(StatType.HEALTH, 1f);
        loadStats();
    }

    @Override
    public void decision(WorldAppState world) {
        Random rand = new Random();
        this.setPath(world.getPathFromTo(position,this.position.addVec3(new Vec3(rand.nextInt(-2,2), position.y, rand.nextInt(-2,2)))));
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){

        Node node = new Node();
        Spatial model = assetManager.loadModel("Models/chicken.glb");
        model.scale(0.5f);
        model.setLocalRotation(new Quaternion().fromAngleAxis((float)(-Math.PI/2), Vector3f.UNIT_Y));

        node.attachChild(model);

        return node;

    }

}
