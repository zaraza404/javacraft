package jogo.systems.inventoryitem.consumableitem.food;

import jogo.gameobject.character.GameCharacter;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;

public abstract class FoodItem extends ConsumableItem {
    float restorationAmount;
    public FoodItem(String itemName, String texturePath, float restorationAmount){
        super(itemName, texturePath);
        this.restorationAmount = restorationAmount;
    }


    public void use(GameCharacter target) {
        target.addHealth(restorationAmount);
    }
}
