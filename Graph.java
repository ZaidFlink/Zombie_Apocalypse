package finalproject;

import java.util.ArrayList;

import finalproject.system.Tile;

public class Graph {
    // TODO level 2: Add fields that can help you implement this data type


    // define an ArrayList to store the vertices in the graph
    public ArrayList<Tile> vertices;
    // define an ArrayList to store the edges in the graph
    private ArrayList<Edge> edges;



    // TODO level 2: initialize and assign all variables inside the constructor
    public Graph(ArrayList<Tile> vertices) {
        this.vertices = vertices;
        // initialize the edges ArrayList
        this.edges = new ArrayList<>();
        // loop over the vertices in the graph
        for (Tile v1 : vertices) {
            // loop over the vertices again
            for (Tile v2 : vertices) {
                // check if the vertices are different and not already connected
                if (v1 != v2 && !this.isConnected(v1, v2) &&
                        (getNeighbors(v1).contains(v2) || getNeighbors(v2).contains(v1))) {
                    // create an edge between the vertices and add it to the edges ArrayList
                    Edge e = new Edge(v1, v2);
                    if (!edges.contains(e)) {
                        this.edges.add(e);
                    }
                }
            }
        }
    }

    public boolean isConnected(Tile v1, Tile v2) {
        // loop over the edges in the graph
        for (Edge e : this.edges) {
            // check if the edge connects the two vertices
            if ((e.getStart() == v1 && e.getEnd() == v2) || (e.getStart() == v2 && e.getEnd() == v1)) {
                return true;
            }
        }
        // if no edge connects the vertices, return false
        return false;
    }


    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight) {
        Edge e = new Edge(origin,destination);
        e.weight = weight;
        edges.add(e);
    }

    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        return edges;
    }

    // TODO level 2: return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors2 = new ArrayList<Tile>();
        for (Edge edges :
                edges) {
            if (edges.origin == t && !(neighbors2.contains(edges.destination))) {
                neighbors2.add(edges.destination);
            }
        }
        return neighbors2;
    }

    // TODO level 2: return total cost for the input path
    public double computePathCost(ArrayList<Tile> path) {
        double totalCost = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Tile t = path.get(i);
            for (Edge e :
                    edges) {
                if (e.getStart() == t && path.get(i+1) == e.getEnd()) {
                    totalCost += e.weight;
                }
            }
        }
        return totalCost;
    }

    public double getWeight(Tile currentNode, Tile neighbor) {

        double w = 0.0;
        for (Edge e :
                edges) {
            if (e.origin.equals(currentNode) && e.destination.equals(neighbor)) {
                w = e.weight;
            }
        }

        return w;
    }


    public static class Edge{
        Tile origin;
        Tile destination;
        double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d){
            super();
            this.origin = s;
            this.destination = d;
        }

        // TODO level 2: getter function 1
        public Tile getStart(){
            return this.origin;
        }


        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

    }

}