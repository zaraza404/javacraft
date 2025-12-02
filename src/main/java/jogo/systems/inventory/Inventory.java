package jogo.systems.inventory;

import jogo.gameobject.character.GameCharacter;
import jogo.systems.StatType;
import jogo.systems.inventoryitem.ItemType;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public class Inventory {
    private InventoryItem[] equipmentItems;
    private InventoryItem[] inventoryItems;
    private int[] inventoryItemsCount;
    private int size;


    public Inventory(int size){
        equipmentItems = new InventoryItem[ItemType.values().length - 1];
        inventoryItems = new InventoryItem[size];
        inventoryItemsCount = new int[size];
        this.size = size;
    }

    public void useItem(ConsumableItem item, GameCharacter target){
        item.use(target);
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] == item){
                removeInventoryItemAt(i);
            }
        }
    }

    public boolean addItem(InventoryItem newItem){
        for (int i = 0; i < inventoryItems.length; i++){
            if (inventoryItems[i] == null) {
                inventoryItems[i] = newItem;
                return true;
            }
        }
        return false;
    }

    public void removeInventoryItemAt(int itemPos){
        inventoryItems[itemPos] = null;
    }
    public void removeEquipmentItemAt(int itemPos){
        equipmentItems[itemPos] = null;
    }

    public void moveItem(int fromPos, int toPos){
        InventoryItem item_a = inventoryItems[fromPos];
        InventoryItem item_b = inventoryItems[toPos];
        inventoryItems[toPos] = item_a;
        inventoryItems[fromPos] = item_b;
    }

    public boolean equipItem(int fromPos, int toPos){
        InventoryItem item = inventoryItems[fromPos];
        if (item != null && toPos < equipmentItems.length) {
            if (item.getItemType().ordinal() == toPos){
                inventoryItems[fromPos] = equipmentItems[toPos];
                equipmentItems[toPos] = item;
                return true;
            }
        }
        return false;
    }

    public boolean unequipItem(int fromPos, int toPos){
        InventoryItem item = equipmentItems[fromPos];
        InventoryItem toItem = inventoryItems[toPos];
        if (toItem != null){
            if (toItem.getItemType().ordinal() == fromPos){
                equipmentItems[fromPos] = toItem;
                inventoryItems[toPos] = item;
                return true;
            }
        } else {
            equipmentItems[fromPos] = null;
            inventoryItems[toPos] = item;
            return true;
        }
        return false;

    }

    public InventoryItem[] getEquipmentItems() { return equipmentItems; }

    public InventoryItem[] getInventoryItems() {
        return inventoryItems;
    }

    public boolean isInventoryFull() {
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] == null){
                return true;
            }
        }
        return false;
    }

    public InventoryItem getInventoryItem(int pos){
        return inventoryItems[pos];
    }

    public InventoryItem getEquipmentItem(int pos){
        return equipmentItems[pos];
    }


    public int getItemPos(InventoryItem item){
        for (int i = 0; i < inventoryItems.length; i++) {
            if (inventoryItems[i] == item){
                return i;
            }
        }
        return -1;
    }

    public float getStat(StatType statType){
        float total = 0f;
        for (InventoryItem item: equipmentItems){
            if (item == null){
                continue;
            }
            total += ((EquipmentItem)item).getStat(statType);
        }
        return total;
    }


    @Override
    public String toString(){
        String str = "";
        for (InventoryItem item : inventoryItems){
            if (item != null){
                str += item.getItemName() + ", ";
            } else {
                str += "EmptySlot, ";
            }
        }
        return str;
    }

}
