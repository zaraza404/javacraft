package jogo.systems.inventoryitem;

public abstract class InventoryItem {
    private String itemName;
    private String texturePath;
    private String hint = "";
    private ItemType itemType;
    protected int level;

    public InventoryItem(String itemName, String texturePath, ItemType itemType, int level) {
        this.itemName = itemName;
        this.texturePath = texturePath;
        this.itemType = itemType;
        this.level = level;
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

    public int getLevel() {return level;}
}
