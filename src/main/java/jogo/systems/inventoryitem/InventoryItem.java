package jogo.systems.inventoryitem;

public abstract class InventoryItem {
    private String itemName;
    private String texturePath;
    private String hint = "";
    private ItemType itemType;

    public InventoryItem(String itemName, String texturePath, ItemType itemType) {
        this.itemName = itemName;
        this.texturePath = texturePath;
        this.itemType = itemType;
    }

    public String getTexturePath(){
        return texturePath;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemType getItemType(){
        return itemType;
    }
}
