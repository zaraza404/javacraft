package jogo.gameobject.character;

import jogo.gameobject.GameObject;
import jogo.systems.StatType;
import jogo.util.Timer;
import jogo.util.WeaponModel;

import java.util.HashMap;

public abstract class GameCharacter extends GameObject {
    private HashMap<StatType, Float> baseStats = new HashMap<>();
    private float maxHealth = 3.0f;
    private float health;
    private float damage = 0.1f;
    private float defence = 0f;
    private final Timer attackTimer = new Timer(1f);
    protected WeaponModel weapon;

    protected GameCharacter(String name) {
        super(name);
        baseStats.put(StatType.HEALTH, 5.0f);
        baseStats.put(StatType.DAMAGE, 1.0f);
        baseStats.put(StatType.DEFENCE, 0.0f);
        loadBaseStats();
        health = maxHealth;
    }

    // update function should be called each frame
    public void update(float tpf){
        attackTimer.update(tpf);
        if (weapon != null){
            weapon.update(tpf);
        }
    }

    public void attack(GameCharacter target) {
        if (attackTimer.isFinished()){
            target.recieveDamage(this.damage);
            attackTimer.start();
            if (weapon != null){
                weapon.startAttack();
            }
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

    public void loadBaseStats(){
        this.maxHealth = baseStats.get(StatType.HEALTH);
        this.health = this.maxHealth;
        this.damage = baseStats.get(StatType.DAMAGE);
        this.defence = baseStats.get(StatType.DEFENCE);
    }

    public void setBaseStats(HashMap<StatType, Float> newBaseStats){
        for (StatType stat: newBaseStats.keySet()){
            baseStats.put(stat, newBaseStats.get(stat));
        }
    }

    public void setAttackSpeed(float attackSpeed){
        attackTimer.setWaitTime(attackSpeed);
    }
    public float getHealthPercentage() {return (this.health / this.maxHealth); }
    public float getHealth() { return this.health; }
    public void setHealth(float health) { this.health = health; }
    public void addHealth(float health) {
        this.health += health;
        if (this.health > this.maxHealth){
            this.health = this.maxHealth;
        }
    }
    public float getMaxHealth() { return this.maxHealth; }
    public void setMaxHealth(float maxHealth) { this.maxHealth = maxHealth + baseStats.get(StatType.HEALTH); }
    public float getDamage() { return this.damage; }
    public void setDamage(float damage) { this.damage = damage + baseStats.get(StatType.DAMAGE); }
}
