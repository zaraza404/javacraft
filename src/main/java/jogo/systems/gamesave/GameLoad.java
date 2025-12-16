package jogo.systems.gamesave;

import jogo.appstate.PlayerAppState;
import jogo.appstate.WorldAppState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class GameLoad {
    private final String saveFilePath = "gamesave.txt";

    public GameLoad(){
    }

    public GameSaveData loadGameData(){
        File gameSaveFile = new File(saveFilePath);

        HashMap<String, Object> textData = new HashMap<>();
        if (gameSaveFile.exists()){
            try {
                Scanner fileReader = new Scanner(gameSaveFile);
                while (fileReader.hasNextLine()){
                    String[] lineData = fileReader.nextLine().split(":");
                    textData.put(lineData[0], lineData[1]);
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (!textData.isEmpty()) {

            int seed = Integer.parseInt((String) textData.get("seed"));
            int floor = Integer.parseInt((String) textData.get("floor"));
            float health = Float.parseFloat((String) textData.get("health"));

            int[][] equipment = itemsStringToItemsInt((String) textData.get("equipment"));
            int[][] inventory = itemsStringToItemsInt((String) textData.get("inventory"));


            GameSaveData gameSaveData = new GameSaveData(seed, floor, health, equipment, inventory);
            return gameSaveData;
        }

        return null;

    }

    private int[][] itemsStringToItemsInt(String itemsString){
        String[] itemsDataString = itemsString.split(";");
        int[][] itemsData = new int[itemsDataString.length][2];

        for (int i = 0; i < itemsData.length; i++){
            String itemTextData = itemsDataString[i];
            if (Objects.equals(itemTextData, " ")) {
                itemsData[i] = new int[]{-1,-1};
            } else {
                itemsData[i][0] = Integer.parseInt(itemTextData.split("-")[0]);
                itemsData[i][1] = Integer.parseInt(itemTextData.split("-")[1]);
            }

        }

        return itemsData;
    }

}
