package jogo.gameobject.character;

import jogo.gameobject.GameObject;
import jogo.systems.StatType;
import jogo.util.Timer;
import jogo.util.WeaponModel;

import java.util.HashMap;

public abstract class GameCharacter extends GameObject {
    private HashMap<StatType, Float> baseStats = new HashMap<>();
    private HashMap<StatType, Float> bonusStats = new HashMap<>();

    private float maxHealth = 3.0f;
    private float health;
    private float damage = 0.1f;
    private float defence = 0f;
    private float attackSpeed = 1f;
    private float movementSpeed = 1f;
    private final Timer attackTimer = new Timer(1f);
    protected WeaponModel weapon;

    protected GameCharacter(String name) {
        super(name);

        baseStats.put(StatType.HEALTH, 5.0f);
        baseStats.put(StatType.DAMAGE, 1.0f);
        baseStats.put(StatType.DEFENCE, 0.0f);
        baseStats.put(StatType.ATTACKSPEED, 1.0f);
        baseStats.put(StatType.MOVEMENTSPEED, 1.0f);

        bonusStats.put(StatType.HEALTH, 0f);
        bonusStats.put(StatType.DAMAGE, 0f);
        bonusStats.put(StatType.DEFENCE, 0f);
        bonusStats.put(StatType.ATTACKSPEED, 0f);
        bonusStats.put(StatType.MOVEMENTSPEED, 0f);

        loadStats();
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

    public void loadStats(){
        this.maxHealth = baseStats.get(StatType.HEALTH) + bonusStats.get(StatType.HEALTH);
        this.damage = baseStats.get(StatType.DAMAGE) + bonusStats.get(StatType.DAMAGE);
        this.defence = baseStats.get(StatType.DEFENCE) + bonusStats.get(StatType.DEFENCE);
        this.attackSpeed = baseStats.get(StatType.ATTACKSPEED) + bonusStats.get(StatType.ATTACKSPEED);
        this.movementSpeed = baseStats.get(StatType.MOVEMENTSPEED) + bonusStats.get(StatType.MOVEMENTSPEED);

        this.attackTimer.setWaitTime(1f/attackSpeed);

    }

    public void setBaseStats(HashMap<StatType, Float> newBaseStats){
        for (StatType stat: newBaseStats.keySet()){
            baseStats.put(stat, newBaseStats.get(stat));
        }
    }

    public void setBaseStat(StatType statType, float value){
        baseStats.put(statType, value);
    }

    public void setBonusStats(HashMap<StatType, Float> newBaseStats){
        for (StatType stat: newBaseStats.keySet()){
            bonusStats.put(stat, newBaseStats.get(stat));
            System.out.println(stat + " " + newBaseStats.get(stat));
        }
        loadStats();
    }

    public void setBonusStat(StatType statType, float value){
        bonusStats.put(statType, value);
        loadStats();
    }

    public float getMaxHealth() { return this.maxHealth; }
    public void setMaxHealth(float maxHealth) { this.maxHealth = maxHealth + baseStats.get(StatType.HEALTH); }

    public float getHealth() { return this.health; }
    public float getHealthPercentage() {return (this.health / this.maxHealth); }
    public void setHealth(float health) { this.health = health; }
    public void addHealth(float health) {
        this.health += health;
        if (this.health > this.maxHealth){
            this.health = this.maxHealth;
        }
    }

    public float getDamage() { return this.damage; }

    public float getMovementSpeed() { return this.movementSpeed; }

}
