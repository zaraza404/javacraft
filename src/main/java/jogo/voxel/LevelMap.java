package jogo.voxel;

import jogo.framework.math.Vec3;
import jogo.gameobject.GameObject;

import java.util.HashMap;

public class LevelMap {
    private String name;
    private int[] dimensions;
    private char[][] map_layout = {
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_','_','_','_','_','_','_','_','_'}

    };
    /*
    private char[][] map_layout = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','.','.','.','#','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','.','M','.','~','.','T','.','M','.','~','.','.','M','.','#'},
            {'#','.','.','.','#','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','.','=','.','#','#','=','#','#','#','#','#','#','=','#'},
            {'#','.','#','=','=','=','=','=','=','=','=','.','.','.','.','#'},
            {'#','.','.','#','.','#','#','~','#','#','#','.','T','.','.','#'},
            {'#','.','#','.','#','.','.','.','.','.','#','.','.','.','.','D'},
            {'#','.','#','.','#','.','M','M','.','.','.','#','#','#','#','#'},
            {'#','.','.','.','#','.','.','.','.','.','#','.','M','.','.','#'},
            {'#','.','.','.','#','#','#','#','#','#','.','.','.','.','.','#'},
            {'#','.','.','.','#','.','.','.','~','.','#','.','.','.','.','#'},
            {'#','.','T','.','.','.','.','.','~','.','.','.','T','.','.','#'},
            {'#','.','M','M','.','#','#','#','~','#','#','.','M','M','.','#'},
            {'#','.','.','~','.','.','.','.','.','.','.','.','.','.','.','#'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
    };*/

    private HashMap<java.lang.Character, byte[][]> layout_dictionary= new HashMap<>();

    public LevelMap(String name, int seed, int floor){
        this.name = name;

        this.map_layout = new LevelMapGenerator().generateMap(seed, floor);
        this.dimensions = new int[]{map_layout.length,map_layout[0].length,8};
        layout_dictionary.put(' ', new byte[][]{{0, 0, 0, 0, 0, 0, 0, 0},{}});

        layout_dictionary.put('E', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{}});
        layout_dictionary.put('B', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,3,0,0,0}});
        layout_dictionary.put('R', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,4,0,0,0}});
        layout_dictionary.put('H', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,5,0,0,0}});
        layout_dictionary.put('C', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,6,0,0,0}});

        layout_dictionary.put('c', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,7,0,0,0}});
        layout_dictionary.put('i', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,8,0,0,0}});

        layout_dictionary.put('.', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{}});
        layout_dictionary.put('#', new byte[][]{{3, 3, 3, 3, 2, 2, 2, 2},{}});
        layout_dictionary.put('=', new byte[][]{{3, 3, 3, 3, 0, 2, 2, 2},{}});
        layout_dictionary.put('≠', new byte[][]{{3, 3, 3, 3, 4, 2, 2, 2},{}});

        layout_dictionary.put('^', new byte[][]{{3, 0, 0, 0, 0, 0, 2, 2},{0,2,0,0,0,0,0,0}});
        layout_dictionary.put('~', new byte[][]{{3, 0, 0, 6, 0, 0, 2, 2},{0,2,0,0,0,0,0,0}});
        layout_dictionary.put('T', new byte[][]{{3, 3, 3, 3, 2, 2, 2, 2},{0,0,0,0,1,0,0,0}});
        layout_dictionary.put('$', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{0,0,0,0,9,0,0,0}});

        layout_dictionary.put('_', new byte[][]{{3, 3, 3, 0, 0, 0, 2, 2},{}});

        layout_dictionary.put('D', new byte[][]{{3, 3, 3, 3, 5, 2, 2, 2},{}});
        layout_dictionary.put('@', new byte[][]{{3, 3, 3, 3, 0, 0, 2, 2},{}});
    }

    public byte[][][] getMapBlockLayout(){
        byte[][][] map_block_layout= new byte[this.dimensions[0]][this.dimensions[1]][this.dimensions[2]];
        for (int z = 0; z < this.dimensions[1]; z++) {
            for (int x = 0; x < this.dimensions[0]; x++) {
                map_block_layout[x][z] = layout_dictionary.get(map_layout[x][z])[0];
            }
        }
        return map_block_layout;
    }

    public HashMap<Vec3, Integer> getGameObjectsLayout(){
        HashMap<Vec3, Integer> gameObjects = new HashMap<>();
        for (int z = 0; z < this.dimensions[1]; z++) {
            for (int x = 0; x < this.dimensions[0]; x++) {
                byte[] vertical = layout_dictionary.get(map_layout[x][z])[1];
                if (vertical.length != 0){
                    for (int y = 0; y < vertical.length; y++){
                        if (vertical[y] != 0){
                            gameObjects.put(new Vec3(x+0.5f,y,z+0.5f), (int) vertical[y]);
                        }
                    }
                }
            }
        }
        return gameObjects;
    }

    public int[] getSpawn() {
        for (int z = 0; z < this.dimensions[1]; z++) {
            for (int x = 0; x < this.dimensions[0]; x++) {
                if (map_layout[z][x] == '@') {
                    return new int[]{z, x};
                }
            }
        }
        return new int[]{10,10};
    }

    public int[] getDimensions(){
        return dimensions;
    }
}
