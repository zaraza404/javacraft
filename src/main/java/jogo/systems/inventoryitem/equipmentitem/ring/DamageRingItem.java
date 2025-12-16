package jogo.systems.inventoryitem.equipmentitem.ring;

import jogo.systems.StatType;

public class DamageRingItem extends RingItem {
    public DamageRingItem(int level) {
        super("Red Ring", "Textures/items/damage_ring.png", level);
        setStat(StatType.DAMAGE, 0.6f, 0.2f);
    }
}
