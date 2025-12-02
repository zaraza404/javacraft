package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class WeaponItem extends EquipmentItem {

    public WeaponItem(String itemName, String texturePath){
        super(itemName, texturePath, ItemType.WEAPON);
    }
}
