package jogo.systems.inventoryitem.equipmentitem.armor;

import jogo.systems.StatType;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class ArmorItem extends EquipmentItem {

    public ArmorItem(String itemName, String texturePath, int level){
        super(itemName, texturePath, ItemType.ARMOR, level);
    }
}
