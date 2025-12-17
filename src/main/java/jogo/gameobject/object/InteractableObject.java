package jogo.gameobject.object;

public interface InteractableObject {

    
    
    public boolean interact();

    public default boolean isDeletedOnInteract(){
        return false;
    };
}
