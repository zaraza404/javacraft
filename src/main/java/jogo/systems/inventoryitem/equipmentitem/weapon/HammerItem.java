package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class HammerItem extends WeaponItem{
    public HammerItem() {
        super("Hammer", "Textures/items/hammer.png");
        this.stats.put(StatType.DAMAGE, 2.0f);
    }
}
