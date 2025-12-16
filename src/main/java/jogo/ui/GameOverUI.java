package jogo.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import jogo.appstate.HudAppState;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;

public class GameOverUI extends UserInterface{
    public GameOverUI(AssetManager assetManager, HudAppState hud, InputAppState input, PlayerAppState player) {
        super("Game Over Interface", assetManager, hud, input, player);
        input.setMouseCaptured(false);
        init();
    }

    @Override
    public void init() {

        SimpleApplication sapp = (SimpleApplication) hud.getApplication();
        int x = sapp.getCamera().getWidth()/2;
        int y = sapp.getCamera().getHeight()/2;


        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText gameOverText = new BitmapText(font);



        gameOverText.setText("You Reached Floor " + player.getFloor() + " \n of the Goblin layer");
        gameOverText.setSize(64);
        gameOverText.setColor(ColorRGBA.fromRGBA255(239,193,95, 255));
        gameOverText.setQueueBucket(RenderQueue.Bucket.Gui);

        gameOverText.setLocalTranslation(x-gameOverText.getLineWidth()/2,y+gameOverText.getHeight()/2,0);

        attachChild(gameOverText);
    }

    @Override
    public void update(float tpf){
        if (input.consumeSelectRequested()){
            hud.getApplication().stop();
        }
    }
}
