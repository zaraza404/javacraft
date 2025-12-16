package jogo.systems.inventoryitem.equipmentitem.ring;

import jogo.systems.StatType;

public class CopperRingItem extends RingItem {
    public CopperRingItem(int level) {
        super("Copper Ring", "Textures/items/copper_ring.png", level);
        setStat(StatType.DEFENCE, 0.3f, 0.1f);
    }
}
