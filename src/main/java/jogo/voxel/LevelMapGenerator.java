package jogo.voxel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class LevelMapGenerator {


    private char[][] mapLayout;
    private Random random;

    private int mapSize = 48;
    private int maxRoomSize = 10;

    private int floor;

    public char[][] generateMap(int seed, int floor){
        mapLayout = new char[mapSize][mapSize];
        for (int z = 0; z <  mapLayout.length; z ++){
            for (int x = 0; x < mapLayout.length; x ++){
                mapLayout[z][x] = '#';
            }
        }

        this.floor = floor;


        random = new Random(seed);

        //Generate Rooms
        generateRooms(8);



        printMap();
        return mapLayout;
    }

    private void generateRooms(int roomCount){
        int[][] roomCenters = new int[roomCount][2];
        for (int i = 0; i < roomCount; i++){
            int[] roomPos = new int[]{random.nextInt(1, mapLayout[0].length-maxRoomSize), random.nextInt(1, mapLayout.length-maxRoomSize)};
            int[] roomSize = new int[]{random.nextInt(5, maxRoomSize), random.nextInt(5, maxRoomSize)};

            generateRoom(roomPos[0],roomPos[1],roomSize[0],roomSize[1]);

            roomCenters[i][0] = random.nextInt(roomPos[0]+2,roomPos[0]+roomSize[0]-2);
            roomCenters[i][1] = random.nextInt(roomPos[1]+2,roomPos[1]+roomSize[1]-2);
        }
        /*for (int[] roomCenter : roomCenters){
            Direction dir;
            if (roomCenter[0] < roomCenter[1]){
                if (roomCenter[0] < (mapSize/2 - roomCenter[1])){
                    dir = Direction.E;
                } else {
                    dir = Direction.N;
                }
            } else {
                if (roomCenter[0] < (mapSize/2 - roomCenter[1])){
                    dir = Direction.S;
                } else {
                    dir = Direction.W;
                }
            }

            generateCorridor(roomCenter[0], roomCenter[1], dir);
        }*/

        for (int i = 0; i < roomCenters.length-1; i++){
            connectTwoRooms(roomCenters[i][0],roomCenters[i][1],roomCenters[i+1][0],roomCenters[i+1][1]);
        }

        mapLayout[roomCenters[0][1]][roomCenters[0][0]] = '@';
        placeEnemies(roomCenters[0][1],roomCenters[0][0]);
        generateDoor(roomCenters[roomCenters.length-1]);
        //mapLayout[roomCenters[roomCenters.length-1][1]][roomCenters[roomCenters.length-1][0]] = 'D';


        cleanWalls();

    }

    private void generateRoom(int pos_x, int pos_z, int size_x, int size_z){  // size_x and size_z have to be at least 5

        for (int x = pos_x; x < pos_x + size_x; x++){
            for (int z = pos_z; z < pos_z + size_z; z++){
                if (x == pos_x || x == size_x-1 || z == pos_z || z == size_z-1){
                    if (random.nextFloat() < 0.05f){
                        mapLayout[z][x] = 'T';
                    } else {
                        mapLayout[z][x] = '#';
                    }

                } else {

                    int tileTypeDecider = random.nextInt();

                    if (tileTypeDecider % 10 == 0){
                        mapLayout[z][x] = 'T';
                    } else if (tileTypeDecider % 7 == 0) {
                        mapLayout[z][x] = 'E';
                    } else {
                        mapLayout[z][x] = '.';
                    }
                }
            }
        }

        for (int x = pos_x; x < pos_x + size_x; x++){
            for (int z = pos_z; z < pos_z + size_z; z++) {
                if (random.nextFloat() < 0.1f){
                    buildSpikePit(z,x);
                }
            }
        }
    }

    /*public void generateCorridor(int startX, int startZ, Direction dir){
        boolean exitedRoom = false;
        boolean PathEnded = false;
        int currX = startX;
        int currZ = startZ;

        int dirX = 0;
        int dirZ = 0;
        switch (dir){
            case W -> {
                dirX = -1;
            }
            case E -> {
                dirX = 1;
            }
            case N -> {
                dirZ = -1;
            }
            case S -> {
                dirZ = 1;
            }

        }

        while (!PathEnded){
            currX += dirX;
            currZ += dirZ;
            if (currX >= mapLayout[0].length || currZ >= mapLayout.length || currX < 0 || currZ < 0){
                break;
            }
            if (!exitedRoom){
                if (mapLayout[currZ][currX] == '#'){
                    mapLayout[currZ][currX] = '=';
                    exitedRoom = true;
                }
            } else {
                if (mapLayout[currZ][currX] == '#' ){
                    mapLayout[currZ][currX] = '=';
                } else {
                    PathEnded = true;
                }
            }
        }
    }*/

    private void connectTwoRooms(int x1, int z1, int x2, int z2){

        boolean secretPassage = random.nextFloat() < 0.1f;

        int startX = Math.min(x1,x2);
        int endX = Math.max(x1,x2);

        for (int x = startX; x <= endX; x++){
            makeWalkable(z1,x);
            if (secretPassage){
                if (mapLayout[z1][x] == '='){
                    mapLayout[z1][x] = '~';
                }
            }

        }

        int startZ= Math.min(z1,z2);
        int endZ = Math.max(z1,z2);
        for (int z = startZ; z < endZ; z++){
            makeWalkable(z,x2);
            if (secretPassage){
                if (mapLayout[z][x2] == '='){
                    mapLayout[z][x2] = '~';
                }
            }
        }

    }

    private void buildSpikePit(int posX, int posZ){
        if (posX < mapLayout[0].length - 2 || posX > 2 || posZ < mapLayout.length - 2 || posZ > 2) {
            for (int z = posZ-1; z <= posZ+1; z++){
                for (int x = posX-1; x <= posX+1; x++){
                    if (mapLayout[z][x] != '#') {
                        mapLayout[z][x] = 'm';
                    }

                }
            }
        }
    }

    public void cleanWalls() {
        for (int z = 1; z < mapLayout.length-1; z++) {
            for (int x = 1; x < mapLayout[z].length-1; x++) {
                if (mapLayout[z][x] == '#'){
                    if (
                            (mapLayout[z+1][x] == '#' || mapLayout[z+1][x] == ' ')
                            && (mapLayout[z-1][x] == '#' || mapLayout[z-1][x] == ' ')
                            && (mapLayout[z][x+1] == '#' || mapLayout[z][x+1] == ' ')
                            && (mapLayout[z][x-1] == '#' || mapLayout[z][x-1] == ' ')){
                        mapLayout[z][x] = ' ';
                    }
                }
            }
        }
    }

    public void generateDoor(int[] roomCenter){
        int currX = roomCenter[0];
        int currZ = roomCenter[1];

        int dirX = 0;
        int dirZ = 0;

        Direction dir = Direction.values()[random.nextInt(4)];
        switch (dir){
            case W -> {
                dirX = -1;
            }
            case E -> {
                dirX = 1;
            }
            case N -> {
                dirZ = -1;
            }
            case S -> {
                dirZ = 1;
            }

        }

        while (true){
            currX += dirX;
            currZ += dirZ;

            if (mapLayout[currZ][currX] == '#') {
                mapLayout[currZ][currX] = 'D';
                break;
            } else {
                makeWalkable(currZ,currX);
            }
        }

    }

    private void makeWalkable(int z, int x){
        mapLayout[z][x] = turnTileWalkable(mapLayout[z][x]);
    }


    private char turnTileWalkable(char tile){
        switch (tile){
            case '.' -> {
                return '.';
            }
            case '#' -> {
                return '=';
            }
            case 'm' -> {
                return 'M';
            }
            case 'M' -> {
                return 'M';
            }
            case 'E' -> {
                return 'E';
            }
            case 'T' -> {
                return '.';
            }
            case '=' -> {
                return '=';
            }
            default -> {
                return '.';
            }
        }
    }

    public void placeEnemies(int pSpawnZ, int pSpawnX){

        int spawnProtectionDistance = 3;

        for (int z = 0; z < mapLayout.length; z++) {
            for (int x = 0; x < mapLayout[z].length; x++) {
                if (mapLayout[z][x] == 'E'){
                    if (z < (pSpawnZ - spawnProtectionDistance) || z > (pSpawnZ + spawnProtectionDistance) || x < (pSpawnX - spawnProtectionDistance) || x < (pSpawnX + spawnProtectionDistance)){
                        mapLayout[z][x] = '.';
                    } else {
                        char[] enemyTypes = new char[]{'B', 'R', 'H', 'C'};
                        int enemyType = random.nextInt(Math.min(enemyTypes.length, floor));

                        mapLayout[z][x] = enemyTypes[enemyType];
                    }
                }
            }
        }

    }

    public void printMap() {
        for (int z = 0; z < mapLayout.length; z++) {
            for (int x = 0; x < mapLayout[z].length; x++) {
                System.out.print(mapLayout[z][x] + " ");
            }
            System.out.println();
        }
    }

    enum Direction{
        N,
        E,
        S,
        W
    }


}
