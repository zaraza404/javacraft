package jogo.gameobject.character;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;

public class EnemyGameCharacter extends NonPlayebleGameCharacter {

    public EnemyGameCharacter(String name){ super(name); }

    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Spatial g = assetManager.loadModel("Models/bob.glb");
        g.scale(0.16666f);
        Quaternion rotation = new Quaternion().fromAngleAxis((float)(Math.PI), Vector3f.UNIT_Y);
        g.setLocalRotation(rotation);
        return g;
    }
}
