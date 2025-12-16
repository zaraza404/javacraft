package jogo.gameobject.character.enemygamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.systems.StatType;
import jogo.util.WeaponModel;

public class GoblinBrute extends EnemyGameCharacter{

    public GoblinBrute(int level){
        super("Brute", level);
        setDropItemsTable(new int[]{1,7,11,13,17});
        setBaseStat(StatType.DAMAGE, 2f + (this.level * 1f));
        setBaseStat(StatType.HEALTH, 8f + (this.level * 6f));
        setBaseStat(StatType.DEFENCE, 1f + (this.level * 0.5f));
        setBaseStat(StatType.ATTACKSPEED, 0.8f + (this.level * 0.1f));
        setBaseStat(StatType.MOVEMENTSPEED, 0.8f);
        loadStats();
        this.setHealth(this.getMaxHealth());
    }



    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }
    @Override
    public Spatial getSpatial(AssetManager assetManager){

        Node node = new Node();
        Spatial model = assetManager.loadModel("Models/brute_goblin.glb");
        model.scale(0.5f);
        model.setLocalRotation(new Quaternion().fromAngleAxis((float)(-Math.PI/2), Vector3f.UNIT_Y));
        weapon = new WeaponModel(assetManager);
        weapon.setLocalTranslation(new Vector3f(0.4f,0.2f,0.4f));

        weapon.setLocalRotation(new Quaternion().fromAngleAxis((float)(Math.PI/2), Vector3f.UNIT_Y));
        node.attachChild(model);
        node.attachChild(weapon);
        weapon.setWeaponModel("Models/battlehammer.glb");
        return node;

    }
}

