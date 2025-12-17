package jogo.gameobject.object;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.gameobject.GameObject;
import jogo.gameobject.GameObjectSpawner;

import java.util.Random;

import static jogo.gameobject.object.InteractableObject.*;

public class LootPot extends GameObject implements InteractableObject{

    int level;

    public LootPot(int level) {
        super("Loot Pot");
        this.level = level;
    }

    @Override
    public boolean interact() {
        if (new Random().nextInt()%2 == 0){
            GameObjectSpawner.getInstance().spawnDropItem(new Random().nextInt(20), level, getPosition());
        }

        return true;
    }

    @Override
    public boolean isDeletedOnInteract(){
        return true;
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Spatial g = assetManager.loadModel("Models/pot.glb");
        g.scale(0.5f);
        Quaternion rotation = new Quaternion().fromAngleAxis((float)(Math.PI), Vector3f.UNIT_Y);
        g.setLocalRotation(rotation);
        g.setLocalTranslation(0.0f, 0, 0.0f);
        Node n = new Node();
        n.attachChild(g);
        return n;
    }

}
