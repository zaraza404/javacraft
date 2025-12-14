package jogo.systems.inventoryitem.equipmentitem.accessory;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class AccessoryItem extends EquipmentItem {

    public AccessoryItem(String itemName, String texturePath, int level){
        super(itemName, texturePath, ItemType.ACCESSORY, level);
    }
}
