package jogo.gameobject.character;

public class Player extends GameCharacter implements Comparable<Player>{
    public int score = 2;
    public Player() {
        super("Player");
    }

    public int compareTo(Player score){
        return (this.score - score.score);
    }
}
