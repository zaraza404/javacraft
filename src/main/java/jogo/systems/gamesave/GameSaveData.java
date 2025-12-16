package jogo.systems.gamesave;

public class GameSaveData {
    private final int seed;
    private final int floor;
    private final float health;
    private final int[][] equipment;
    private final int[][] inventory;

    public GameSaveData(int seed, int floor, float health, int[][] equipment, int[][] inventory) {
        this.seed = seed;
        this.floor = floor;
        this.health = health;
        this.equipment = equipment;
        this.inventory = inventory;
    }

    public int getSeed() {return seed;}
    public int getFloor() {return floor;}
    public float getHealth() {return health;}
    public int[][] getEquipment() {return equipment;}
    public int[][] getInventory() {return inventory;}

}
