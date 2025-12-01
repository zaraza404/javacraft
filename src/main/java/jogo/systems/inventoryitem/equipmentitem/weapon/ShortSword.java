package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class ShortSword extends WeaponItem{
    public ShortSword() {
            super("ShortSword", "Textures/items/short_sword.png");
            this.stats.put(StatType.DAMAGE, 1.0f);
        }
}

