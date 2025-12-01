package jogo.systems.inventoryitem.consumableitem;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.InventoryItem;

public class ConsumableItem extends InventoryItem {
    public ConsumableItem(String itemName, String texturePath){
           super(itemName, texturePath, ItemType.CONSUMABLE);
    }
}
