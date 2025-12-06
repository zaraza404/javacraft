package jogo.gameobject.item;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import jogo.gameobject.GameObject;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.InventoryItemRegistry;

import java.lang.reflect.InvocationTargetException;

public class PickableItem extends GameObject {

    InventoryItem item;

    public PickableItem(byte itemId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super("Pickable Item");
        this.item = InventoryItemRegistry.defaultPalette().get(itemId);

    }

    public InventoryItem pickUp(){
        return item;
    }

    @Override
    public Spatial getSpatial(AssetManager assetManager){
        Quad quad = new Quad(1f,1f);
        Geometry obj_visual = new Geometry(getName(), quad);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Lighting.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/items/hammer.png"));
        obj_visual.setMaterial(mat);
        return obj_visual;
    }

}
