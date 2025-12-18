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
    Vector2f healthBarDim = new Vector2f(512f, 128f);
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
        crosshair.setPosition(x-32f,y-32f);

        Picture healthBarUnder = new Picture("Health Under Picture");
        healthBarUnder.setImage(assetManager, "Interface/healthbar_under.png", true);
        healthBarUnder.setHeight(healthBarDim.y);
        healthBarUnder.setWidth(healthBarDim.x+128);
        attachChild(healthBarUnder);
        healthBarUnder.setPosition((x - (healthBarDim.x+128)/2f),600f);

        healthBarFill = new Picture("Health Fill Picture");
        healthBarFill.setImage(assetManager, "Interface/healthbar_fill.png", true);
        healthBarFill.setHeight(healthBarDim.y);
        healthBarFill.setWidth(healthBarDim.x);
        attachChild(healthBarFill);
        healthBarFill.setPosition(x - (healthBarDim.x/2f),600f);


    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        healthBarFill.setWidth(Math.min(healthBarDim.x * player.getPlayer().getHealthPercentage(), healthBarDim.x));
    }

}
