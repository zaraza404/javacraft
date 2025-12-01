package jogo.gameobject.character;

import jogo.gameobject.GameObject;
import jogo.util.Timer;

public abstract class GameCharacter extends GameObject {
    private float maxHealth = 3.0f;
    private float health;
    private float damage = 0.1f;
    private float defence = 0f;
    private final Timer attackTimer = new Timer(1.0f);

    protected GameCharacter(String name) {
        super(name);
        health = maxHealth;
    }

    // update function should be called each frame
    public void update(float tpf){
        attackTimer.update(tpf);
    }

    public void attack(GameCharacter target) {
        if (attackTimer.isFinished()){
            target.recieveDamage(this.damage);
            attackTimer.start();
        }
    }

    public void recieveDamage(float dmg) {
        float damage_dealt = Math.max(0.1f, dmg - defence);
        health -= damage_dealt;
        if (health <= 0) {
            this.onDeath();
        }
    }

    public void onDeath() {
        return;
    }

    public float getHealthPercentage() {return (this.health / this.maxHealth); }
    public float getHealth() { return this.health; }
    public void setHealth(float health) { this.health = health; }
    public float getMaxHealth() { return this.maxHealth; }
    public void setMaxHealth(float maxHealth) { this.maxHealth = maxHealth; }
    public float getDamage() { return this.damage; }
    public void setDamage(float damage) { this.damage = damage; }

}
