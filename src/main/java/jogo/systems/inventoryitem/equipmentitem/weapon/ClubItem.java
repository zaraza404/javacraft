package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.StatType;

public class ClubItem extends WeaponItem{
    public ClubItem(int level) {
        super("Club", "Textures/items/club.png","Models/club.glb", level);
        setStat(StatType.DAMAGE, 3.0f, 0.45f);
        setStat(StatType.ATTACKSPEED, 0.0f, 0.03f);
    }
}
