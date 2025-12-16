package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;

public class LeatherArmorItem extends ArmorItem{
    public LeatherArmorItem(int level) {
        super("Leather Armor", "Textures/items/leather_armor.png", level);
        setStat(StatType.DEFENCE, 2f, 0.5f);
        setStat(StatType.MOVEMENTSPEED, 0.4f, 0f);
    }
}
