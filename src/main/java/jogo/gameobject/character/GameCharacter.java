package jogo.gameobject.character;

import jogo.gameobject.GameObject;
import jogo.util.Timer;

public abstract class GameCharacter extends GameObject {
    private float max_health = 3.0f;
    private float health = 10.0f;
    private float damage = 1.1f;
    private final Timer attack_timer = new Timer(1.0f);

    protected GameCharacter(String name) {
        super(name);
        health = max_health;
    }

    // update function should be called each frame
    public void update(float tpf){
        attack_timer.update(tpf);
    }

    public void attack(GameCharacter target) {
        if (attack_timer.isFinished()){
            target.recieveDamage(this.damage);
            attack_timer.start();
        }

    }


    public void recieveDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            this.onDeath();
        }
    }

    public void onDeath() {
        return;
    }


    public float getHealth() { return this.health; }
    public void setHealth(float health) { this.health = health; }
    public float getMaxHealth() { return this.max_health; }
    public void setMaxHealth(float max_health) { this.max_health = max_health; }
    public float getDamage() { return this.damage; }
    public void setDamage(float damage) { this.damage = damage; }

}
