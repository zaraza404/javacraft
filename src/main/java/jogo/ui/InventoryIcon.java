package jogo.ui;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

public class InventoryIcon extends Node {

    private ClickablePicture picture;
    private BitmapText levelText;

    public InventoryIcon(String name, AssetManager assetManager, String texturePath, int level) {
        super(name);

        picture = new ClickablePicture(name + "_pic");
        picture.setImage(assetManager, texturePath, true);
        picture.getMaterial().getTextureParam("Texture").getTextureValue().setMagFilter(Texture.MagFilter.Nearest);
        picture.setWidth(64);
        picture.setHeight(64);
        attachChild(picture);


        addLevelText(assetManager, level);

    }

    private void addLevelText(AssetManager assetManager, int level) {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        levelText = new BitmapText(font);

        levelText.setText(String.valueOf(level));
        levelText.setSize(24);
        levelText.setColor(ColorRGBA.fromRGBA255(239,193,95, 255));
        levelText.setQueueBucket(RenderQueue.Bucket.Gui);

        levelText.setLocalTranslation(picture.getWidth() - levelText.getLineWidth() - 4, picture.getHeight()/2-4, 1);

        attachChild(levelText);
    }

    public ClickablePicture getPicture() {
        return picture;
    }
}

