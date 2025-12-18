package jogo.gameobject.object;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import jogo.gameobject.GameObject;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.InventoryItemRegistry;
import jogo.util.Billboard;

public class PickableItem extends GameObject {
    double posOffcet = 0;
    InventoryItem item;
    boolean pickedUp = false;

    public PickableItem(int itemId, int level){
        super("Pickable Item");
        this.item = InventoryItemRegistry.defaultPalette().get(itemId, level);
    }

    public InventoryItem pickUp(){
        return item;
    }

    public float getOffcet(float tpf){
        posOffcet += tpf;
        return (float) Math.sin(posOffcet)/10;
    }

    public void setPickedUp() {
        this.pickedUp = true;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        return (new Billboard(name, 0.5f, item.getTexturePath(), assetManager));
    }

}
