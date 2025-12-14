package jogo.gameobject;

import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.InventoryItemRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GameObjectRegistry {
    private final ArrayList<Class> objTypes = new ArrayList<>();

    public int register(int id ,Class objType) {
        if (objTypes.size() < id){
            for (int i = objTypes.size()-1; i < id; i++){
                objTypes.add(i, null);
            }
        }
        objTypes.add(id, objType);
        return id;
    }

    public GameObject get(int id)  {
        Class<?> itemClass = objTypes.get(id);
        if (itemClass == null){
            throw new IllegalArgumentException("No GameObjectWithId " + id);
        }
        Constructor<?> constructor = null;
        try {
            constructor = itemClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Object doesnt have a consructor");
        }

        GameObject obj = null;
        try {
            obj = (GameObject) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }


    public int size() { return objTypes.size(); }

    public static GameObjectRegistry defaultPalette(){
        GameObjectRegistry r = new GameObjectRegistry();
        try {
            r.register(0, Class.forName("jogo.gameobject.character.Player"));
            r.register(1, Class.forName("jogo.gameobject.object.Torch"));
            r.register(2, Class.forName("jogo.gameobject.object.Spikes"));
            r.register(3, Class.forName("jogo.gameobject.character.LeatherGoblin"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return r;
    }
}
