package jogo.voxel.blocks;

import jogo.voxel.TexturedVoxelBlockType;

public class DoorBlockType extends TexturedVoxelBlockType {
    public DoorBlockType() {
        super("door", "Textures/blocks/door.png");
    }

    @Override
    public boolean interact(){
        System.out.println("Next Level Reached");
        return  true;
    }
}
