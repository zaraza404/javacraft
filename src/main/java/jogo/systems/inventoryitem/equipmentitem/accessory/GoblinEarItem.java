package jogo.systems.inventoryitem.equipmentitem.accessory;

import jogo.systems.StatType;

public class GoblinEarItem extends AccessoryItem {
    public GoblinEarItem(int level) {
        super("Heavy Armor", "Textures/items/goblin_ear.png", level);
        setStat(StatType.MOVEMENTSPEED, 1.0f);
    }
}
