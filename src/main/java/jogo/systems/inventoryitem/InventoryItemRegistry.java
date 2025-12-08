package jogo.systems.inventoryitem;

import java.lang.reflect.*;
import java.util.ArrayList;

public class InventoryItemRegistry {
    private final ArrayList<Class> itemTypes = new ArrayList<>();

    public int register(int id, Class itemType) {
        itemTypes.add(id, itemType);
        if (id > 255) throw new IllegalStateException("Too many item types (>255)");
        return id;
    }

    public InventoryItem get(int id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> itemClass = itemTypes.get(id);
        Constructor<?> constructor = itemClass.getDeclaredConstructor();
        InventoryItem item = (InventoryItem) constructor.newInstance();
        return item;
    }


    public int size() { return itemTypes.size(); }

    public static InventoryItemRegistry defaultPalette() throws ClassNotFoundException {
        InventoryItemRegistry p = new InventoryItemRegistry();

        p.register(0, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));    //0
        p.register(1, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem"));     //1
        p.register(2, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem"));    //2

        return p;
    }
}

