package jogo.systems.inventoryitem.equipmentitem.ring;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class RingItem extends EquipmentItem {

    public RingItem(String itemName, String texturePath, int level){
        super(itemName, texturePath, ItemType.RING, level);
    }
}
