package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;
import java.util.Arrays;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    private ArrayList<Tile> graph;
    public ShortestPath(Tile start) {
        super(start);
        this.source = start;
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        // Use DFS to generate the graph
        ArrayList<Tile> tileArrayList = GraphTraversal.DFS(source);
        this.g = new Graph(tileArrayList);

        for (Tile t1:
            tileArrayList ) {
            ArrayList<Tile> isWalkableList = isWalkable(t1.neighbors);
            for (Tile t2 :
                    isWalkableList) {
                if (t1 instanceof MetroTile && t2 instanceof MetroTile) {
                    g.addEdge(t1, t2, ((MetroTile) t2).metroDistanceCost);
                } else {
                    g.addEdge(t1, t2, t2.distanceCost);
                }
            }
        }
	}
    
    private ArrayList<Tile> isWalkable(ArrayList<Tile> path) {
        ArrayList<Tile> walkableList = new ArrayList<>();
        for (Tile t :
                path) {
            if (t.isWalkable()) {
                walkableList.add(t);
            }
        }
        return walkableList;
    }

//    private Tile findLeastCost(ArrayList<Tile> tiles) {
//        Tile leastCost = tiles.get(0);
//        for (Tile t :
//                tiles) {
//            if (t.distanceCost < leastCost.distanceCost) {
//                leastCost = t;
//            }
//        }
//        return leastCost;
//    }
}