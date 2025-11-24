package jogo.util.pathfinding;

import jogo.framework.math.Vec3;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Pathfinding {
    private ArrayList<int[]> walkable_positions;
    private float y_pos;

    public Pathfinding(){
        walkable_positions = new ArrayList<int[]>();

    }

    public ArrayList<Vec3> BuildPath(Vec3 from_pos, Vec3 to_pos, ArrayList<Vec3> map){
        y_pos = from_pos.y;
        walkable_positions.clear();
        for (int i = 0; i < map.size(); i++) {
            walkable_positions.add(map.get(i).toPointXZ()) ;
        }
        ArrayList<PathfindingNode> open = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();

        int[] from_point = from_pos.toPointXZ();
        int[] to_point = to_pos.toPointXZ();

        open.add(new PathfindingNode(from_point, 0, getDistance(from_point,to_point), null));

        while (!open.isEmpty()) {
            Collections.shuffle(open);
            open.sort(null);
            PathfindingNode curr = open.remove(0);

            if (curr.is_point(to_point)){
                return (getPath(curr));
            }

            String pos_str = curr.point[0] + "-" + curr.point[1];
            if (visited.contains(pos_str)){
                continue;
            }
            visited.add(pos_str);

            for(int[] neighbor : getNeighbours(curr.point)){
                if (isWalkable(neighbor) && !visited.contains(neighbor[0] + "-" + neighbor[1])) {
                    int new_steps = curr.steps + 1;
                    int new_distance = getDistance(neighbor, to_point);
                    open.add(new PathfindingNode(neighbor, new_steps, new_distance, curr));

                }
            }
        }
        return new ArrayList<>();
    }

    private boolean isWalkable(int[] point){
        for (int[] walkable_point : walkable_positions){
            if (walkable_point[0] == point[0] && walkable_point[1] == point[1]){
                return true;
            }
        }
        return false;
    }

    private int getDistance(int[] a, int[] b){
        return(Math.abs(a[0]-b[0])+Math.abs(a[1]-b[1]));
    }

    private int[][] getNeighbours(int[] point){
        return (new int[][] {
                {point[0]+1,point[1]},
                {point[0]-1,point[1]},
                {point[0],point[1]+1},
                {point[0],point[1]-1}
        });

    }

    private ArrayList<Vec3> getPath(PathfindingNode end){
        ArrayList<Vec3> path= new ArrayList<>();
        PathfindingNode curr_node = end;
        while (curr_node!=null){
            path.add(new Vec3(curr_node.point[0],y_pos,curr_node.point[1]));
            curr_node = curr_node.parent;
        }
        Collections.reverse(path);

        return path;

    }


}
