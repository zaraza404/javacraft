package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class WeaponItem extends EquipmentItem {

    public WeaponItem(String itemName, String texturePath, int level){
        super(itemName, texturePath, ItemType.WEAPON, level);
    }
}
