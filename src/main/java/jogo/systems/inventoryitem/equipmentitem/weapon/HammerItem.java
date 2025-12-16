package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class HammerItem extends WeaponItem{
    public HammerItem(int level) {
        super("Hammer", "Textures/items/hammer.png","Models/battlehammer.glb", level);
        setStat(StatType.DAMAGE, 5.0f, 0.8f);
        setStat(StatType.ATTACKSPEED, -0.3f, 0.03f);
        setStat(StatType.MOVEMENTSPEED, -0.3f, 0);
        setStat(StatType.DEFENCE, 1f, 0.08f);
    }
}
