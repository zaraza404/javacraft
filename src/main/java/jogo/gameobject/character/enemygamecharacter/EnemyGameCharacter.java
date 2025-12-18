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
    public EnemyGameCharacter(String name, int level){ super(name); this.level = level;}


    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }

}
