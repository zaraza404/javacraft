package jogo.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import jogo.ui.*;

public class HudAppState extends BaseAppState {

    public final Node guiNode;
    private final AssetManager assetManager;
    private PlayerAppState player;
    private InputAppState input;

    private UserInterface currentUI;

    enum UIType{
        OVERLAY,
        INVENTORY
    }

    public HudAppState(Node guiNode, AssetManager assetManager, PlayerAppState player, InputAppState input) {
        this.guiNode = guiNode;
        this.assetManager = assetManager;
        this.player = player;
        this.input = input;
    }

    @Override
    protected void initialize(Application app) {
        changeUI(UIType.OVERLAY);
    }

    private void changeUI(UIType newUIType) {
        player.updateStats();

        if (currentUI != null){
            guiNode.detachChild(currentUI);
        }

        UserInterface newUI = switch (newUIType) {
            case OVERLAY -> new OverlayUI(assetManager, this, input, player);
            case INVENTORY -> new InventoryUI(assetManager, this, input, player);
        };

        if (newUI != null){
            guiNode.attachChild(newUI);
            currentUI = newUI;
        }
    }

    @Override
    public void update(float tpf) {
        if (input.consumeInventoryRequested()){
            if (currentUI instanceof InventoryUI){
                changeUI(UIType.OVERLAY);
            } else {
                changeUI(UIType.INVENTORY);
            }
        }
        if (currentUI != null){
            currentUI.update(tpf);
        }
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() { }

    @Override
    protected void onDisable() { }
}

