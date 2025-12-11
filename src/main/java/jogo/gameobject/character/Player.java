package jogo.gameobject.character;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.util.WeaponModel;

public class Player extends GameCharacter implements Comparable<Player>{
    public int score = 2;
    public Player() {
        super("Player");
        setAttackSpeed(0.2f);
    }

    public int compareTo(Player score){
        return (this.score - score.score);
    }

    public void setWeapon(WeaponModel weapon){
        this.weapon = weapon;
    }


}
