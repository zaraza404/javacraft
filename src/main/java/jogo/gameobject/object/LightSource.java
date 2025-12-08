package jogo.gameobject.object;

import com.jme3.light.PointLight;

public interface LightSource {

    public default PointLight[] getLight(){
        PointLight light = new PointLight();
        return new PointLight[]{light};
    }
}
