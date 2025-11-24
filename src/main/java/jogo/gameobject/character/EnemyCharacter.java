package jogo.gameobject.character;

import com.jme3.math.Vector3f;
import jogo.appstate.WorldAppState;
import jogo.framework.math.Vec3;
import jogo.voxel.VoxelWorld;

public class EnemyCharacter extends NonPlayebleCharacter{

    public EnemyCharacter(String name){ super(name); }

    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
        System.out.println("Generated path:");
        for (Vec3 waypoint : path) {
            System.out.println("  Waypoint: " + waypoint);
        }
    }


}
