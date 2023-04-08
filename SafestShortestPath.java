package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.source = start;
		this.health = health;
		generateGraph();
	}


	public void generateGraph() {
		// TODO Auto-generated method stub

		ArrayList<Tile> tileArrayList2 = GraphTraversal.DFS(source);
		this.aggregatedGraph = new Graph(tileArrayList2);
		this.damageGraph = new Graph(tileArrayList2);
		this.costGraph = new Graph(tileArrayList2);

		for (Tile t1 :
				tileArrayList2) {
			ArrayList<Tile> isWalkableList = isWalkable(t1.neighbors);
			for (Tile t2 :
					isWalkableList) {
				if (t1 instanceof MetroTile && t2 instanceof MetroTile) {
					aggregatedGraph.addEdge(t1, t2, t1.damageCost + t2.distanceCost);
					damageGraph.addEdge(t1, t2, t1.damageCost + t2.distanceCost);
					costGraph.addEdge(t1, t2, ((MetroTile) t2).metroDistanceCost);
				} else {
					aggregatedGraph.addEdge(t1, t2, t2.damageCost);
					damageGraph.addEdge(t1, t2, t2.damageCost);
					costGraph.addEdge(t1, t2, t2.distanceCost);
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


	private void getGraph(Graph G){
		super.g = G;
	}

	@Override
	public ArrayList<Tile> findPath(Tile startNode, LinkedList<Tile> waypoints) {
		getGraph(costGraph);
		int count = 0;
// Find the optimal path pc with the least distance cost
		ArrayList<Tile> pc = super.findPath(startNode, waypoints);

// If the total damage cost for pc is less than our health H, return pc
		double pcLambda = getTotalDamageCost(pc);
		if (pcLambda < health) {
			return pc;
		}

		getGraph(damageGraph);

		ArrayList<Tile> pd = super.findPath(startNode, waypoints);

		double pdLambda = getTotalDamageCost(pd);
		if (pdLambda > health) {
			return null;
		}

		ArrayList<Tile> path = new ArrayList<>();
		while (path.isEmpty() && count != 20) {
			// Compute the multiplier λ using the equation:
			double lambda = (getTotalDistanceCost(pc) - getTotalDistanceCost(pd)) / (getTotalDamageCost(pd) - getTotalDamageCost(pc));

			for (Graph.Edge e :
					aggregatedGraph.getAllEdges()) {
				e.weight = e.destination.distanceCost + lambda * e.destination.damageCost;
			}

			getGraph(aggregatedGraph);

			ArrayList<Tile> pr = super.findPath(startNode, waypoints);

			double prLambda = getTotalAggregatedCost(pr);

			if (prLambda == pcLambda) {
				path = pd;
			} else if (prLambda < health) {
				pd = pr;
			} else {
				pc = pr;
			}
			count++;

		}
		if (waypoints.isEmpty()) {
			path = findPath(startNode, getDestination(g));
		}
//		else {
//			path = findPath(startNode, waypoints);
//		}

		return path;
	}

	private double getTotalAggregatedCost(ArrayList<Tile> path) {
		double totalCost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalCost += path.get(i).damageCost;
		}
		return totalCost;
	}

	private double getTotalDistanceCost(ArrayList<Tile> path) {
		double totalCost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalCost += path.get(i).distanceCost;
		}
		return totalCost;
	}


	public double getTotalDamageCost(ArrayList<Tile> path) {
		double totalCost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalCost += path.get(i).damageCost;
		}
		return totalCost;
	}

}