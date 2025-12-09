package jogo.voxel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class LevelMapGenerator {


    private char[][] mapLayout;
    private Random random;

    private int mapSize = 64;
    private int maxRoomSize = 10;

    public char[][] generateMap(int seed){
        mapLayout = new char[mapSize][mapSize];
        for (int z = 0; z <  mapLayout.length; z ++){
            for (int x = 0; x < mapLayout.length; x ++){
                mapLayout[z][x] = '#';
            }
        }


        random = new Random(seed);

        //Generate Rooms
        generateRooms(7);

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

        mapLayout[roomCenters[roomCenters.length-1][1]][roomCenters[roomCenters.length-1][0]] = 'D';

    }

    private void generateRoom(int pos_x, int pos_z, int size_x, int size_z){  // size_x and size_z have to be at least 5

        for (int x = pos_x; x < pos_x + size_x; x ++){
            for (int z = pos_z; z < pos_z + size_z; z ++){
                if (x == pos_x || x == size_x-1 || z == pos_z || z == size_z-1){
                    if (random.nextFloat() < 0.05f){
                        mapLayout[z][x] = 'T';
                    } else {
                        mapLayout[z][x] = '#';
                    }

                } else {

                    float tileTypeDecider = random.nextFloat();

                    if (tileTypeDecider < 0.02){
                        mapLayout[z][x] = 'L';
                    } else if (tileTypeDecider < 0.04){
                        mapLayout[z][x] = 'E';
                    } else if (tileTypeDecider < 0.2){
                        mapLayout[z][x] = 'M';
                    } else if (tileTypeDecider < 0.21){
                        mapLayout[z][x] = 'T';
                    } else {
                        mapLayout[z][x] = '.';
                    }
                }
            }
        }
    }

    public void generateCorridor(int startX, int startZ, Direction dir){
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
    }

    private void connectTwoRooms(int x1, int z1, int x2, int z2){

        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++){
            if (x >= 0 && x < mapLayout[0].length && z1 >= 0 && z1 < mapLayout.length){
                if (mapLayout[z1][x] == '#'){
                    mapLayout[z1][x] = '=';
                }
            }
        }

        int startZ = Math.min(z1, z2);
        int endZ = Math.max(z1, z2);
        for (int z = startZ; z <= endZ; z++){
            if (x2 >= 0 && x2 < mapLayout[0].length && z >= 0 && z < mapLayout.length){
                if (mapLayout[z][x2] == '#'){
                    mapLayout[z][x2] = '=';
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
