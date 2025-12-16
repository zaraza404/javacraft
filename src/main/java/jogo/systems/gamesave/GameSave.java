package jogo.systems.gamesave;


import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.InventoryItemRegistry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GameSave {

    private final String saveFilePath = "gamesave.txt";
    public GameSave(){

    }
    // Game saves data in format of:
    // "type_of_value:data"

    // for item lists format is:
    // "list_type: item1_id-item1_level;item2_id-item2_level; ;item4_id-item4_level;..." - empty space means that slot is empty
    public void saveGame(int dungeonSeed, int currentFloor, float currentHealth, InventoryItem[] equipmentItems, InventoryItem[] inventoryItems){

        String saveData = "";

        saveData += "seed:" + dungeonSeed + "\n";
        saveData += "floor:" + currentFloor + "\n";
        saveData += "health:" + currentHealth + "\n";

        String equipmentData = "equipment:";
        for (int i = 0; i < equipmentItems.length; i++){
            if (equipmentItems[i] != null){
                InventoryItem item = equipmentItems[i];
                String itemData = InventoryItemRegistry.defaultPalette().getItemId(item) + "-" + item.getLevel() + ";";
                equipmentData += itemData;
            } else {
                equipmentData += " ;";
            }
        }

        saveData += equipmentData + "\n";

        String inventoryData = "inventory:";
        for (int i = 0; i < inventoryItems.length; i++){
            if (inventoryItems[i] != null){
                InventoryItem item = inventoryItems[i];
                String itemData = InventoryItemRegistry.defaultPalette().getItemId(item) + "-" + item.getLevel() + ";";
                inventoryData += itemData;
            } else {
                inventoryData += " ;";
            }
        }

        saveData += inventoryData + "\n";




        try {
            FileWriter saveWriter = new FileWriter(saveFilePath);
            saveWriter.write(saveData);
            saveWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void deleteSave(){
        File saveFile = new File(saveFilePath);
        if (saveFile.exists()){
            saveFile.delete();
        }
    }

}
