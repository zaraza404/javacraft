package jogo.systems.inventoryitem.equipmentitem.accessory;

import jogo.systems.StatType;

public class MetalBugItem extends AccessoryItem {
    public MetalBugItem(int level) {
        super("Metal Bug", "Textures/items/metal_bug.png", level);
        setStat(StatType.DEFENCE, 1f, 0.2f);
        setStat(StatType.MOVEMENTSPEED, 0.2f, 0.05f);
    }
}
