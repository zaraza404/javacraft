package jogo.systems.inventoryitem.consumableitem.potion;

import jogo.gameobject.character.GameCharacter;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;
import jogo.util.Timer;

public abstract class PotionItem extends ConsumableItem {

    Timer timer;
    float effect_duration;

    public PotionItem(String itemName, String texturePath, float effect_duration, int level){
        super(itemName, texturePath, level);
        this.effect_duration = effect_duration;
        timer = new Timer(effect_duration);
    }

    public abstract void use(GameCharacter target);
}
