package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;

public class PeaceArmorItem extends ArmorItem{
    public PeaceArmorItem(int level) {
        super("Peace Armor", "Textures/items/peace_armor.png", level);
        setStat(StatType.HEALTH, 10.0f, 1f);
        setStat(StatType.DEFENCE, 2.0f, 0.4f);
        setStat(StatType.MOVEMENTSPEED, 0.1f, 0f);
    }
}
