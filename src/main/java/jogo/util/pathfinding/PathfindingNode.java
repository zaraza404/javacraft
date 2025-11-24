package jogo.util.pathfinding;

public class PathfindingNode implements Comparable<PathfindingNode>{
    int[] point;

    PathfindingNode parent;

    int steps;
    int distance;

    public PathfindingNode(int[] point, int steps, int distance, PathfindingNode parent) {
        this.point = point;

        this.parent = parent;

        this.steps = steps;
        this.distance = distance;

    }

    public boolean is_point(int[] point){
        if (point[0] == this.point[0] && point[1] == this.point[1]){
            return (true);
        }
        return (false);
    }

    public int get_cost(){
        return (this.steps + this.distance);
    }

    @Override
    public int compareTo(PathfindingNode o) {
        return ( this.get_cost() - o.get_cost());
    }
}
