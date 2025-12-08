package jogo.gameobject.object;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.GameCharacter;

public class Spikes extends AffectObject {
    public float dps = 0.001f;
    public Spikes() {
        super("spikes", 0.7f);
    }

    @Override
    public void affect(GameCharacter character, float tpf) {
        character.recieveDamage(dps * tpf);
    }
    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Spatial g = assetManager.loadModel("Models/spikes.glb");
        g.scale(0.5f);
        Quaternion rotation = new Quaternion().fromAngleAxis((float)(Math.PI), Vector3f.UNIT_Y);
        g.setLocalRotation(rotation);
        g.setLocalTranslation(0.0f, 0, 0.0f);
        Node n = new Node();
        n.attachChild(g);
        return n;
    }


}
