package jogo.gameobject.character.enemygamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.systems.StatType;
import jogo.util.WeaponModel;

public class GoblinBatrak extends EnemyGameCharacter{

    public GoblinBatrak(int level){
        super("Batrak", level);
        setDropItemsTable(new int[]{0,4,8,16});
        setBaseStat(StatType.DAMAGE, 1f + (this.level * 0.5f));
        setBaseStat(StatType.HEALTH, 4f + (this.level * 2f));
        setBaseStat(StatType.DEFENCE, 0f + (this.level * 0f));
        setBaseStat(StatType.ATTACKSPEED, 1f + (this.level * 0.1f));
        setBaseStat(StatType.MOVEMENTSPEED, 1f);
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
        Spatial model = assetManager.loadModel("Models/batrak_goblin.glb");
        model.scale(0.5f);
        model.setLocalRotation(new Quaternion().fromAngleAxis((float)(-Math.PI/2), Vector3f.UNIT_Y));
        weapon = new WeaponModel(assetManager);
        weapon.setLocalTranslation(new Vector3f(0.4f,0.2f,0.4f));

        weapon.setLocalRotation(new Quaternion().fromAngleAxis((float)(Math.PI/2), Vector3f.UNIT_Y));
        node.attachChild(model);
        node.attachChild(weapon);
        weapon.setWeaponModel("Models/club.glb");
        return node;

    }
}

