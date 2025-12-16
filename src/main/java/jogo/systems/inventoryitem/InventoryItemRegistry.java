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

    public InventoryItem get(int id, int level) {

        Class<?> itemClass = itemTypes.get(id);
        Constructor<?> constructor = null;
        try {
            constructor = itemClass.getDeclaredConstructor(int.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        InventoryItem item = null;
        try {
            item = (InventoryItem) constructor.newInstance(level);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    public int getItemId(InventoryItem item){
        for (int i = 0; i < itemTypes.size(); i++){

            if (itemTypes.get(i).isInstance(item)){
                return i;
            }
        }
        return -1;
    }


    public int size() { return itemTypes.size(); }

    public static InventoryItemRegistry defaultPalette() {
        InventoryItemRegistry p = new InventoryItemRegistry();

        try {
            p.register(0, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BerriesItem"));
            p.register(1, Class.forName("jogo.systems.inventoryitem.consumableitem.food.BreadItem"));
            p.register(2, Class.forName("jogo.systems.inventoryitem.consumableitem.food.ChickenLegItem"));
            p.register(3, Class.forName("jogo.systems.inventoryitem.consumableitem.food.ChocolateItem"));

            p.register(4, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.ClubItem"));
            p.register(5, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.SwordItem"));
            p.register(6, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.AxeItem"));
            p.register(7, Class.forName("jogo.systems.inventoryitem.equipmentitem.weapon.HammerItem"));

            p.register(8, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.GoblinEarItem"));
            p.register(9, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.NatureNecklaceItem"));
            p.register(10, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.ImpsHeadItem"));
            p.register(11, Class.forName("jogo.systems.inventoryitem.equipmentitem.accessory.MetalBugItem"));

            p.register(12, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.LeatherArmorItem"));
            p.register(13, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.HeavyArmorItem"));
            p.register(14, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.PeaceArmorItem"));
            p.register(15, Class.forName("jogo.systems.inventoryitem.equipmentitem.armor.RageArmorItem"));

            p.register(16, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.CopperRingItem"));
            p.register(17, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DiamondRingItem"));
            p.register(18, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.NatureRingItem"));
            p.register(19, Class.forName("jogo.systems.inventoryitem.equipmentitem.ring.DamageRingItem"));



        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return p;
    }
}

