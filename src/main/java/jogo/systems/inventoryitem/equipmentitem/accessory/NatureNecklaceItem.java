package jogo.systems.inventoryitem.equipmentitem.accessory;

import jogo.systems.StatType;

public class NatureNecklaceItem extends AccessoryItem {
    public NatureNecklaceItem(int level) {
        super("Nature Necklace", "Textures/items/health_necklase.png", level);
        setStat(StatType.HEALTH, 4f, 1f);
    }
}
