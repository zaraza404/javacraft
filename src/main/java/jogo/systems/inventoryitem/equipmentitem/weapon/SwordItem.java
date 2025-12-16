package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class SwordItem extends WeaponItem{
    public SwordItem(int level) {
            super("ShortSword", "Textures/items/short_sword.png", "Models/sword.glb", level);
            setStat(StatType.DAMAGE, 2.0f, 0.3f);
            setStat(StatType.ATTACKSPEED, 0.5f, 0.05f);
          setStat(StatType.MOVEMENTSPEED, 0.5f, 0.05f);
        }
}

