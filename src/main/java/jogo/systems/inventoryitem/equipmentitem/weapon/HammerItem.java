package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class HammerItem extends WeaponItem{
    public HammerItem(int level) {
        super("Hammer", "Textures/items/hammer.png", level);
        setStat(StatType.DAMAGE, 1.0f);
        setStat(StatType.MOVEMENTSPEED, 1.0f);
    }
}
