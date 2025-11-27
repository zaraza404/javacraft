package jogo.gameinterface;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<InventoryItem> inventoryItems;
    private ArrayList<Integer> inventoryItemsCount;

    private int[][] item_positions = new int[][] {
            {-1,0},
            {0,0},
            {1,0},
            {-1,1},
            {0,1},
            {1,1}
    };
    public Inventory(){
        inventoryItems = new ArrayList<>();
        inventoryItemsCount = new ArrayList<>();


    }

    public void addItem(InventoryItem new_item){
        inventoryItems.add(new_item);
    }

    public void removeItemAt(int itemPos){
        inventoryItems.remove(itemPos);
    }

    public float[] getItemIterfacePositionAt(int itemPos){
        return new float[] {(133.0f * item_positions[itemPos][0]), (133.0f * item_positions[itemPos][1])};
    }

    public ArrayList<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }



}
