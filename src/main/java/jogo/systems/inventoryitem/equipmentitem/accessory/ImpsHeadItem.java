package jogo.systems.inventoryitem.equipmentitem.accessory;

import jogo.systems.StatType;

public class ImpsHeadItem extends AccessoryItem {
    public ImpsHeadItem(int level) {
        super("Imps Head", "Textures/items/imp_head.png", level);
        setStat(StatType.DAMAGE, 1f, 0.3f);
    }
}
