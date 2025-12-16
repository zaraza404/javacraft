package jogo.util;

import com.jme3.anim.AnimComposer;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.framework.math.Vec3;

public class WeaponModel extends Node {
    AssetManager assetManager;
    Spatial weaponSpatial;

    boolean weaponUp = true;

    Timer attackTimer = new Timer(0.1f);
    public WeaponModel(AssetManager assetManager){
        this.assetManager = assetManager;
        setWeaponModel("Models/club.glb");
    }

    public void setWeaponModel(String weaponModelPath){
        if (weaponSpatial != null){
            weaponSpatial.removeFromParent();
        }

        weaponSpatial = assetManager.loadModel(weaponModelPath);
        weaponSpatial.setLocalTranslation(0,0.2f,0);
        weaponSpatial.scale(0.86666f);
        attachChild(weaponSpatial);


    }

    public void update(float tpf){
        attackTimer.update(tpf);

        if (attackTimer.isFinished() && !weaponUp){
            weaponUp = true;
            weaponSpatial.rotate(new Quaternion().fromAngleAxis(-(float) Math.PI/4, Vector3f.UNIT_Z));
        }
    }

    public void startAttack(){
        if (!weaponUp) return;
        attackTimer.start();
        weaponSpatial.rotate(new Quaternion().fromAngleAxis((float) Math.PI/4, Vector3f.UNIT_Z));
        weaponUp = false;
    }
}
