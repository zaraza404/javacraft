package jogo.gameobject;

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

    public GameObject get(int id, int level)  {
        Class<?> itemClass = objTypes.get(id);
        if (itemClass == null){
            throw new IllegalArgumentException("No GameObjectWithId " + id);
        }

        GameObject obj = null;

        try {
            Constructor<?> constructor = itemClass.getDeclaredConstructor(int.class);
            obj = (GameObject) constructor.newInstance(level);
        } catch (NoSuchMethodException e) {
            // If no int constructor, try no-arg constructor
            try {
                Constructor<?> constructor = itemClass.getDeclaredConstructor();
                obj = (GameObject) constructor.newInstance();
            } catch (NoSuchMethodException | InstantiationException |
                     IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException("Object must have either a no-arg constructor or a constructor with int parameter", ex);
            }
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
            r.register(3, Class.forName("jogo.gameobject.character.enemygamecharacter.GoblinBatrak"));
            r.register(4, Class.forName("jogo.gameobject.character.enemygamecharacter.GoblinRogue"));
            r.register(5, Class.forName("jogo.gameobject.character.enemygamecharacter.GoblinBrute"));
            r.register(6, Class.forName("jogo.gameobject.character.enemygamecharacter.GoblinCursed"));
            r.register(7, Class.forName("jogo.gameobject.object.LootPot"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return r;
    }
}
