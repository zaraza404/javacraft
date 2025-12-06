package jogo.engine;

import jogo.gameobject.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRegistry {
    private final List<GameObject> objects = new ArrayList<>();
    private final List<GameObject> objectsToRemove = new ArrayList<>();

    public synchronized void add(GameObject obj) {
        if (obj != null && !objects.contains(obj)) {
            objects.add(obj);
        }
    }

    public synchronized void startRemove(GameObject obj) {
        objectsToRemove.add(obj);
        objects.remove(obj);
    }

    public void completeRemove(GameObject obj){
        objectsToRemove.remove(obj);
    }



    public synchronized List<GameObject> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(objects));
    }
    public synchronized List<GameObject> getAllToRemove() {
        return Collections.unmodifiableList(new ArrayList<>(objectsToRemove));
    }

}

