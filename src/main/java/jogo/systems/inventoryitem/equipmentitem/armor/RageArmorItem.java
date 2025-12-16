package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;

public class RageArmorItem extends ArmorItem{
    public RageArmorItem(int level) {
        super("Rage Armor", "Textures/items/rage_armor.png", level);
        setStat(StatType.HEALTH, 3.0f, 0.75f);
        setStat(StatType.DAMAGE, 2f, 0.3f);
        setStat(StatType.MOVEMENTSPEED, 0.3f, 0f);
        setStat(StatType.ATTACKSPEED, 0.2f, 0.05f);
    }
}
