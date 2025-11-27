package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import jogo.gameinterface.Inventory;
import jogo.gameinterface.InventoryItem;

public class HudAppState extends BaseAppState {

    private final Node guiNode;
    private final AssetManager assetManager;
    private BitmapText crosshair;
    private Picture healthBarPicture;
    private PlayerAppState player;

    public HudAppState(Node guiNode, AssetManager assetManager, PlayerAppState player) {
        this.guiNode = guiNode;
        this.assetManager = assetManager;
        this.player = player;
    }

    @Override
    protected void initialize(Application app) {

        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        crosshair = new BitmapText(font, false);
        crosshair.setText("+");
        crosshair.setSize(font.getCharSet().getRenderedSize() * 2f);
        guiNode.attachChild(crosshair);
        centerCrosshair();

        healthBarPicture = new Picture("Health Picture");
        healthBarPicture.setImage(assetManager, "Textures/CharacterBanners/gabe.png", true);
        healthBarPicture.setWidth(200f);
        healthBarPicture.setHeight(200f);
        guiNode.attachChild(healthBarPicture);
        positionHealthHUD();

        System.out.println("HudAppState initialized: crosshair attached");
        displayInventory();
    }

    private void centerCrosshair() {
        SimpleApplication sapp = (SimpleApplication) getApplication();
        int w = sapp.getCamera().getWidth();
        int h = sapp.getCamera().getHeight();
        float x = (w - crosshair.getLineWidth()) / 2f;
        float y = (h + crosshair.getLineHeight()) / 2f;
        crosshair.setLocalTranslation(x, y, 0);
    }

    private void positionHealthHUD() {
        SimpleApplication sapp = (SimpleApplication) getApplication();
        int w = sapp.getCamera().getWidth();
        int h = sapp.getCamera().getHeight();
        float x = (w - 200f) / 2f;
        float y = (h + 200) / 2f;
        healthBarPicture.setLocalTranslation(x,y,0);
    }

    public void displayInventory() {
        SimpleApplication sapp = (SimpleApplication) getApplication();
        Inventory inventory = player.inventory;
        int x = sapp.getCamera().getWidth()/2;
        int y = sapp.getCamera().getHeight()/2;
        Picture inventoryBackground = new Picture("Inventory Background");
        inventoryBackground.setImage(assetManager, "Textures/GameInterface/inventoryBackground.png", true);
        inventoryBackground.setHeight(400f);
        inventoryBackground.setWidth(400f);
        guiNode.attachChild(inventoryBackground);
        inventoryBackground.setPosition(500,200);

        int i = 0;
        for (InventoryItem item : inventory.getInventoryItems()){
            Picture inventoryItem = new Picture("Inventory Item " + i);
            inventoryItem.setImage(assetManager, item.getTexturePath(), true);
            inventoryItem.setHeight(136f);
            inventoryItem.setWidth(136f);
            guiNode.attachChild(inventoryItem);
            float[] item_offset = inventory.getItemIterfacePositionAt(i);
            inventoryItem.setPosition(x + item_offset[0], y + item_offset[1]);
            i++;
        }
    }

    @Override
    public void update(float tpf) {
        // keep centered (cheap)
        centerCrosshair();
        healthBarPicture.setWidth((player.getPlayer().getHealth() / player.getPlayer().getMaxHealth()) * 200f);

    }

    @Override
    protected void cleanup(Application app) {
        if (crosshair != null) crosshair.removeFromParent();
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}

