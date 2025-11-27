package jogo.gameinterface;

public abstract class InventoryItem {
    private String item_name;
    private String texture_path;
    private String hint = "";

    public InventoryItem(String item_name, String texture_path) {
        this.item_name = item_name;
        this.texture_path = texture_path;
    }

    public String getTexturePath(){
        return texture_path;
    }
}
