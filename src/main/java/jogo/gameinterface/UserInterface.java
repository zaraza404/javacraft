package jogo.gameinterface;

import com.jme3.scene.Node;
import com.jme3.ui.Picture;

public abstract class UserInterface extends Node {
    private String name;
    private Picture background;
    public UserInterface(String name) {
        super(name);
        this.init();
    }

    public abstract void init();

    public void update(float tpf){

    }
}
