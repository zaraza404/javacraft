package jogo.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;
import jogo.appstate.HudAppState;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;

public class OverlayUI extends UserInterface{
    Picture healthBarFill;
    Picture crosshsair;
    Vector2f healthBarDim = new Vector2f(200f, 200f);
    public OverlayUI(AssetManager assetManager, HudAppState hud, InputAppState input, PlayerAppState player){
        super("Overlay",assetManager,hud,input,player);
        input.setMouseCaptured(true);
        init();
    }

    @Override
    public void init() {
        SimpleApplication sapp = (SimpleApplication) hud.getApplication();
        float x = sapp.getCamera().getWidth() / 2f;
        float y = sapp.getCamera().getHeight() / 2f;

        Picture crosshair = new Picture("Crosshair", false);
        crosshair.setImage(assetManager, "Interface/crosshair.png", true);
        crosshair.setWidth(64f);
        crosshair.setHeight(64f);
        attachChild(crosshair);
        crosshair.setPosition(x,y);

        healthBarFill = new Picture("Health Picture");
        healthBarFill.setImage(assetManager, "Textures/CharacterBanners/gabe.png", true);
        healthBarFill.setLocalScale(200, 50, 0);
        attachChild(healthBarFill);
        healthBarFill.setPosition((x - healthBarDim.x/2f),(y + healthBarDim.y/2f));


    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        healthBarFill.setWidth(healthBarDim.x * player.getPlayer().getHealthPercentage());
    }

}
