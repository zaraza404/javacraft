package jogo.gameobject.character;

import jogo.appstate.WorldAppState;

public class EnemyGameCharacter extends NonPlayebleGameCharacter {

    public EnemyGameCharacter(String name){ super(name); }

    @Override
    public void decision(WorldAppState world) {
        this.setPath(world.getPathFromTo(position,world.getPlayerPosition()));
    }


}
