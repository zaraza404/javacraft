package jogo.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class Billboard extends Node {
    public Billboard(String name, float quadSize, String texturePath, AssetManager assetManager){
        super(name);
        Geometry g = new Geometry(name, new Quad(quadSize,quadSize));

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = assetManager.loadTexture(texturePath);
        texture.setMagFilter(Texture.MagFilter.Nearest);
        mat.setTexture("DiffuseMap", texture);
        mat.setBoolean("UseMaterialColors",true);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular",ColorRGBA.White);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        g.setQueueBucket(RenderQueue.Bucket.Transparent);

        g.setMaterial(mat);
        attachChild(g);
        g.setLocalTranslation(-(quadSize/2),0,0);
    }
}
