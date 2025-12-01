package jogo.ui;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import jogo.appstate.HudAppState;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;

public abstract class UserInterface extends Node {
    private String name;

    protected AssetManager assetManager;
    protected HudAppState hud;
    protected InputAppState input;
    protected PlayerAppState player;

    public UserInterface(String name, AssetManager assetManager, HudAppState hud, InputAppState input, PlayerAppState player) {
        super(name);
        this.name = name;
        this.assetManager = assetManager;
        this.hud = hud;
        this.input = input;
        this.player = player;
    }

    public abstract void init();

    public void update(float tpf){

    }
}
