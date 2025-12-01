package jogo.ui;

import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;

public class ClickablePicture extends Picture {
    public ClickablePicture(String name){
        super(name, false);
    }

    public boolean checkClick(Vector2f clickPosition){

        float left_border = getWorldTranslation().x;
        float right_border = getWorldTranslation().x + getWorldScale().x;
        float top_border = getWorldTranslation().y + getWorldScale().y;
        float bottom_border = getWorldTranslation().y;

        if ((clickPosition.x > left_border && clickPosition.x < right_border)
            && (clickPosition.y > bottom_border && clickPosition.y < top_border)){
            System.out.println("Clicked " + name);

            return true;
        }

        return false;
    }
}
