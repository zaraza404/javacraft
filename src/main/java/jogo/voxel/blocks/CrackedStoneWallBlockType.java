package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;
import jogo.util.ProcTextures;
import jogo.voxel.TexturedVoxelBlockType;
import jogo.voxel.VoxelBlockType;

public class CrackedStoneWallBlockType extends TexturedVoxelBlockType {
    public CrackedStoneWallBlockType() {
        super("cracked_stone_wall","Textures/blocks/cracked_stone_wall.png");
        this.breakable = true;
    }

}
