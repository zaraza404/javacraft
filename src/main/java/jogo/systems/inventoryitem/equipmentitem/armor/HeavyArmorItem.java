package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;

public class HeavyArmorItem extends ArmorItem{
    public HeavyArmorItem(int level) {
        super("Heavy Armor", "Textures/items/heavy_armor.png", level);
        setStat(StatType.HEALTH, 5.0f, 1f);
        setStat(StatType.DEFENCE, 3f, 0.3f);
        setStat(StatType.MOVEMENTSPEED, -0.5f, 0f);
    }
}
