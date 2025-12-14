package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;

public class HeavyArmorItem extends ArmorItem{
    public HeavyArmorItem(int level) {
        super("Heavy Armor", "Textures/items/heavy_armor.png", level);
        setStat(StatType.HEALTH, 5.0f);
        setStat(StatType.DEFENCE, 0.5f);
        setStat(StatType.MOVEMENTSPEED, -0.5f);
    }
}
