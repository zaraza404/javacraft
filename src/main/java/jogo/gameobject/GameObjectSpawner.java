package jogo.gameobject;

import jogo.engine.GameRegistry;
import jogo.framework.math.Vec3;
import jogo.gameobject.character.Player;

import java.util.HashMap;

public class GameObjectSpawner {

    private static GameObjectSpawner instance;
    private GameRegistry registry;
    private GameObjectSpawner(GameRegistry registry){
        this.registry = registry;
    }

    public static GameObjectSpawner getInstance(){
        return instance;
    }

    public static GameObjectSpawner getInstance(GameRegistry registry){
        if (instance == null){
            instance = new GameObjectSpawner(registry);
        }
        return instance;
    }

    public GameObject spawnGameObject(int id, Vec3 pos){
        GameObject obj = GameObjectRegistry.defaultPalette().get(id);
        obj.setPosition(pos);
        if (!(obj instanceof Player)){
            registry.add(obj);
        }
        return obj;
    }

    public void SpawnObjects(HashMap<Vec3, Integer> objects){
        for (Vec3 pos : objects.keySet()){
            spawnGameObject(objects.get(pos), pos);
        }
    }
}
