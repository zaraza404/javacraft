package jogo.gameobject.object;

import jogo.framework.math.Vec3;
import jogo.gameobject.GameObject;
import jogo.gameobject.character.GameCharacter;
import jogo.gameobject.character.Player;

public abstract class AffectObject extends GameObject {
    public float affectRadius = 0f;
    public AffectObject (String name, float affectRadius){
        super(name);
        this.affectRadius = affectRadius;
    }
    public boolean isInAffectRadius(Vec3 pos){
        return position.distanceTo(pos) < affectRadius;
    }
    public abstract void affect(GameCharacter character, float tpf);
}
