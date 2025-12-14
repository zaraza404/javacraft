package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class SwordItem extends WeaponItem{
    public SwordItem(int level) {
            super("ShortSword", "Textures/items/short_sword.png", level);
            setStat(StatType.DAMAGE, 1.0f);
            setStat(StatType.ATTACKSPEED, 1.0f);
        }
}

