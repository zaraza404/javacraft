package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

import java.util.HashMap;

public class Hammer extends WeaponItem{
    public Hammer() {
        super("Hammer", "Textures/items/hammer.png");
        this.stats.put(StatType.DAMAGE, 2.0f);
    }
}
