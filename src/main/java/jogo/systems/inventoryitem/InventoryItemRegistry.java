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

    public InventoryItem get(int id) {

        Class<?> itemClass = itemTypes.get(id);
        Constructor<?> constructor = null;
        try {
            constructor = itemClass.getDeclaredConstructor(int.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        InventoryItem item = null;
        try {
            item = (InventoryItem) constructor.newInstance(1);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return item;
    }


    public int size() { return itemTypes.size(); }

    public static InventoryItemRegistry defaultPalette() {
        InventoryItemRegistry p = new InventoryItemRegistry();

        try {
            p.register(0, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));
            p.register(1, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));
            p.register(2, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));
            p.register(3, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));

            p.register(4, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem"));
            p.register(5, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem"));
            p.register(6, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem"));
            p.register(7, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem"));

            p.register(8, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem"));
            p.register(9, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem"));
            p.register(10, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem"));
            p.register(11, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem"));

            p.register(12, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem"));
            p.register(13, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem"));
            p.register(14, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem"));
            p.register(15, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem"));

            p.register(16, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem"));
            p.register(17, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem"));
            p.register(18, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem"));
            p.register(19, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem"));



        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return p;
    }
}

