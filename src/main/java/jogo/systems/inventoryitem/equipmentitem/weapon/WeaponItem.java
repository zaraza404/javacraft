package jogo.systems.inventoryitem.equipmentitem.weapon;

import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public abstract class WeaponItem extends EquipmentItem {
    private String modelPath;

    public WeaponItem(String itemName, String texturePath, String modelPath, int level){
        super(itemName, texturePath, ItemType.WEAPON, level);
        this.modelPath = modelPath;
    }

    public String getModelPath() {
        return modelPath;
    }
}
