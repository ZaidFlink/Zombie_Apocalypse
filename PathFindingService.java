package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class PathFindingService {
    Tile source;
    Graph g;

    public PathFindingService(Tile start) {
        this.source = start;
    }

    private ArrayList<Tile> getAllNodes(ArrayList<Graph.Edge> edges) {
        ArrayList<Tile> allNodes = new ArrayList<>();
        for (Graph.Edge e :
                edges) {
            if (!allNodes.contains(e.origin)) {
                allNodes.add(e.origin);
            }
        }
        return allNodes;
    }

    public abstract void generateGraph();

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        // Initialize the distance of the start node to 0 and the distance of all other nodes to infinity
        for (Tile node : getAllNodes(g.getAllEdges())) {
            node.distanceCost = Double.POSITIVE_INFINITY;
//            if (node == startNode) {
//
//            }
        }
        startNode.distanceCost = 0;

        TilePriorityQ tilePriorityQ = new TilePriorityQ(getAllNodes(g.getAllEdges()));

        // Initialize the unvisited nodes set to contain all nodes in the graph
        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> unvisited = getAllNodes(g.getAllEdges());
        // While there are unvisited nodes

        while (!unvisited.isEmpty()) {

            Tile currentTile = tilePriorityQ.removeMin();
            if (visited.contains(currentTile)) {
                continue;
            } else {
                visited.add(currentTile);
                unvisited.remove(currentTile);
            }

            // If the current node is the destination, we have found the minimum weight path
            if (currentTile.isDestination) {
                break;
            }

            // Update the distance of the unvisited neighbors of the current node
            for (Tile neighbor : currentTile.neighbors) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                double newDistance = currentTile.distanceCost + g.getWeight(currentTile, neighbor);
                if (newDistance < neighbor.distanceCost) {
                    neighbor.distanceCost = newDistance;
                    neighbor.predecessor = currentTile;
                }
            }
        }

        // Use the predecessor information to backtrack and find the path to the destination
        ArrayList<Tile> path = new ArrayList<>();
        Tile currentNode = getDestination(g);

        while (currentNode != startNode.predecessor) {
            path.add(0, currentNode);
            currentNode = currentNode.predecessor;
        }

        return path;
    }

    public Tile getDestination(Graph g) {
        for (Tile node : getAllNodes(g.getAllEdges())) {
            if (node.isDestination) {
                return node;
            }
        }
        return null;
    }




    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> path = new ArrayList<>();
        ArrayList<Tile> maybePath = findPath(start);
        Tile currentNode = end;

        while (currentNode != start.predecessor) {
            if (maybePath.contains(currentNode)) {
                path.add(0, currentNode);
            }
            currentNode = currentNode.predecessor;

        }

        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){

        Tile destination = getDestination(g);
        ArrayList<Tile> finalPath = new ArrayList<>();

        if (waypoints.isEmpty()) {
            return removeDuplicates(findPath(start, destination));
        }

        if (waypoints.size() == 1) {
            finalPath.addAll(findPath(start, waypoints.get(0)));
            finalPath.addAll(findPath(waypoints.get(0), destination));
        } else {

            finalPath = new ArrayList<>(findPath(start, waypoints.get(0)));

            //While loop
            for (int i = 1; i < waypoints.size() - 1; i++) {
                finalPath.addAll(findPath(waypoints.get(i - 1), waypoints.get(i)));
            }

            finalPath.addAll(findPath(waypoints.get(waypoints.size() - 1), destination));

        }


        //TODO THIS IS HARD CODED FIX WHEN YOU CAN
//        int count = 0;
//        for (int i = 0; i < finalPath.size()-1; i++) {
//            if (finalPath.get(i) instanceof DesertTile) {
//                count = 5;
//            } else if (count == 5) {
//                finalPath.remove(finalPath.get(i));
//                count = 0;
//            }
//        }

        printPath(removeDuplicates(finalPath));

        return(removeDuplicates(finalPath));
    }

    public void printPath(ArrayList<Tile> path) {
        for (Tile t :
                path) {
            System.out.println(t);
        }
    }

    private ArrayList<Tile> removeDuplicates(ArrayList<Tile> list) {

        // Create a new ArrayList
        ArrayList<Tile> newList = new ArrayList<Tile>();

        // Traverse through the first list
        for (Tile element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}