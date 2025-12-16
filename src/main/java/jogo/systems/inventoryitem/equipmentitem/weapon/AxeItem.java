package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class AxeItem extends WeaponItem{
    public AxeItem(int level) {
        super("Axe", "Textures/items/axe.png","Models/battleaxe.glb", level);
        setStat(StatType.DAMAGE, 4.0f, 0.6f);
        setStat(StatType.ATTACKSPEED, 0.3f, 0.03f);
        setStat(StatType.MOVEMENTSPEED, 0.3f, 0);
    }
}
