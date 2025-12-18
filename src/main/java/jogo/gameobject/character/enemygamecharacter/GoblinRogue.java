package jogo.gameobject.character.enemygamecharacter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.appstate.WorldAppState;
import jogo.systems.StatType;
import jogo.util.WeaponModel;

public class GoblinRogue extends EnemyGameCharacter{

    public GoblinRogue(int level){
        super("Rogue", level);
        setDropItemsTable(new int[]{0,1,5,12,16});
        setBaseStat(StatType.DAMAGE, 2f + (this.level * 0.35f));
        setBaseStat(StatType.HEALTH, 5f + (this.level * 1.5f));
        setBaseStat(StatType.DEFENCE, 0.5f + (this.level * 0.2f));
        setBaseStat(StatType.ATTACKSPEED, 1.4f + (this.level * 0.2f));
        setBaseStat(StatType.MOVEMENTSPEED, 1.3f + (this.level * 0.05f));
        loadStats();
        this.setHealth(this.getMaxHealth());
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
        weapon.setWeaponModel("Models/sword.glb");
        return node;

    }
}

