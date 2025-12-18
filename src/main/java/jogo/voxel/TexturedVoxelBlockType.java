package jogo.voxel;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public abstract class TexturedVoxelBlockType extends VoxelBlockType{
    private String texturePath;
    public TexturedVoxelBlockType(String name, String texturePath){
        super(name);
        this.texturePath = texturePath;
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {
        Material m = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture t = assetManager.loadTexture(texturePath);
        t.setMagFilter(Texture.MagFilter.Nearest);
        m.setTexture("DiffuseMap", t);

        return m;
    }
}
