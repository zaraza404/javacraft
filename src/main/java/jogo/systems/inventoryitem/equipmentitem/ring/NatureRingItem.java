package jogo.systems.inventoryitem.equipmentitem.ring;

import jogo.systems.StatType;

public class NatureRingItem extends RingItem {
    public NatureRingItem(int level) {
        super("Nature Ring", "Textures/items/health_ring.png", level);
        setStat(StatType.DEFENCE, 0.3f, 0.1f);
    }
}
