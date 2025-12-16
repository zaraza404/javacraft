package jogo.systems.inventoryitem.equipmentitem.ring;

import jogo.systems.StatType;

public class DiamondRingItem extends RingItem {
    public DiamondRingItem(int level) {
        super("Diamond Ring", "Textures/items/armor_ring.png", level);
        setStat(StatType.DEFENCE, 1.0f, 0.2f);
    }
}
