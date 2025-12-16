package jogo.systems.inventoryitem.equipmentitem;

import jogo.systems.StatType;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.InventoryItem;

import java.util.HashMap;

public abstract class EquipmentItem extends InventoryItem {
    protected HashMap<StatType, Float> stats;
    public EquipmentItem(String item_name, String texture_path, ItemType item_type, int level){
        super(item_name, texture_path, item_type, level);

        stats = new HashMap<>();
        for (StatType s: StatType.values()){
            stats.put(s, 0.0f);
        }

    }

    public float getStat(StatType statType) {
        return stats.get(statType);
    }

    public void setStat(StatType stat, float value, float levelValue){
        stats.put(stat, value + (levelValue * this.level));
    }
}
