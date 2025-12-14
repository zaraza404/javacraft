package jogo.systems.inventoryitem.consumableitem;

import jogo.gameobject.character.GameCharacter;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.InventoryItem;

public abstract class ConsumableItem extends InventoryItem {
    public ConsumableItem(String itemName, String texturePath, int level){
           super(itemName, texturePath, ItemType.CONSUMABLE, level);
    }

    public abstract void use(GameCharacter target);
}
