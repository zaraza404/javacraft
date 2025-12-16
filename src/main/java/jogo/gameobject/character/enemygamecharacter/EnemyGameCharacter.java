package jogo.gameobject.character.enemygamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.gameobject.character.NonPlayebleGameCharacter;
import jogo.systems.StatType;
import jogo.util.WeaponModel;

public abstract class EnemyGameCharacter extends NonPlayebleGameCharacter {

    int level;
    public EnemyGameCharacter(String name, int level){ super(name); }


    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Node node = new Node();
        Spatial model = assetManager.loadModel("Models/bob.glb");
        model.scale(0.16666f);
        weapon = new WeaponModel(assetManager);
        weapon.setLocalTranslation(new Vector3f(0.35f,0.2f,0.35f));
        weapon.setLocalRotation(new Quaternion().fromAngleAxis((float)(Math.PI/1.8), Vector3f.UNIT_Y));
        node.attachChild(model);
        node.attachChild(weapon);
        return node;
    }

}
