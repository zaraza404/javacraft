package jogo.gameobject.character;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.util.WeaponModel;

public class LeatherGoblin extends EnemyGameCharacter{

    public LeatherGoblin(){ super("Leather Goblin"); }



    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }
    @Override
    public Spatial getSpatial(AssetManager assetManager){

        Node node = new Node();
        Spatial model = assetManager.loadModel("Models/lether_goblin.glb");
        model.scale(0.5f);
        model.setLocalRotation(new Quaternion().fromAngleAxis((float)(-Math.PI/2), Vector3f.UNIT_Y));
        weapon = new WeaponModel(assetManager);
        weapon.setLocalTranslation(new Vector3f(0.4f,0.2f,0.4f));

        weapon.setLocalRotation(new Quaternion().fromAngleAxis((float)(Math.PI/2), Vector3f.UNIT_Y));
        node.attachChild(model);
        node.attachChild(weapon);
        return node;

    }
}

