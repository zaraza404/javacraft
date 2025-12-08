package jogo.gameobject.object;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jogo.gameobject.GameObject;

public class Torch extends GameObject implements LightSource{

    public Torch(){
        super("Torch");
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Node n = new Node();
        Spatial[] geometries = new Spatial[]{assetManager.loadModel("Models/torch.glb"),assetManager.loadModel("Models/torch.glb"),assetManager.loadModel("Models/torch.glb"),assetManager.loadModel("Models/torch.glb")};

        for (Spatial g : geometries){
            g.scale(0.5f);
            n.attachChild(g);
        }

        geometries[0].setLocalTranslation(0f, 0, 0.55f);
        geometries[0].setLocalRotation(new Quaternion(0.25f,0f,0f,1f));

        geometries[1].setLocalTranslation(-0f, 0, -0.55f);
        geometries[1].setLocalRotation(new Quaternion(-0.25f,0f,0f,1f));

        geometries[2].setLocalTranslation(-0.55f, 0, 0f);
        geometries[2].setLocalRotation(new Quaternion(0f,0f,0.25f,1f));

        geometries[3].setLocalTranslation(0.55f, 0, 0f);
        geometries[3].setLocalRotation(new Quaternion(0f,0f,-0.25f,1f));

        return n;
    }

    @Override
    public PointLight[] getLight(){
        PointLight[] lights = new PointLight[]{new PointLight(),new PointLight(),new PointLight(),new PointLight()};
        Vector3f[] lPositions = new Vector3f[]{new Vector3f(-0f, 0.2f, -0.55f), new Vector3f(-0f, 0.2f, 0.55f),new Vector3f(-0.55f, 0.2f, 0),new Vector3f(0.55f, 0.2f, 0)};
        for (int i = 0; i < lights.length; i++) {
            lights[i].setPosition(lPositions[i].add(getPosition().toVector3f()));
            lights[i].setColor(ColorRGBA.fromRGBA255(251, 212, 84, 255));
            lights[i].setRadius(12f);
        }

        return lights;
    }
}
