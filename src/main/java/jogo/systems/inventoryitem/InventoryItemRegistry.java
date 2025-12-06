package jogo.systems.inventoryitem;

import java.lang.reflect.*;
import java.util.ArrayList;



public class InventoryItemRegistry {
    private final ArrayList<Class> itemTypes = new ArrayList<>();

    public byte register(Class itemType) {
        itemTypes.add(itemType);
        int id = itemTypes.size() - 1;
        if (id > 255) throw new IllegalStateException("Too many item types (>255)");
        return (byte) id;
    }

    public InventoryItem get(byte id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int idx = Byte.toUnsignedInt(id);

        Class<?> itemClass = itemTypes.get(idx);
        Constructor<?> constructor = itemClass.getDeclaredConstructor();
        InventoryItem item = (InventoryItem) constructor.newInstance();
        return item;
    }


    public int size() { return itemTypes.size(); }

    public static InventoryItemRegistry defaultPalette() throws ClassNotFoundException {
        InventoryItemRegistry p = new InventoryItemRegistry();

        p.register(Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));    //0
        p.register(Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem"));     //1
        p.register(Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem"));    //2

        return p;
    }
}

