package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;


public class GraphTraversal {
	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {

		// create an empty queue to store the nodes to visit
		ArrayList<Tile> queue = new ArrayList<>();
		// create an empty ArrayList to store the visited nodes
		ArrayList<Tile> visited = new ArrayList<>();
		// add the start node to the queue and visited list

		queue.add(s);
		visited.add(s);

		// loop while the queue is not empty
		while (!queue.isEmpty()) {
			// remove the first node from the queue
			Tile current = queue.remove(0);
			// loop over the neighbors of the current node
			for (Tile neighbor : current.neighbors) {
				// check if the neighbor has not been visited
				if (!(visited.contains(neighbor))) {
					queue.add(neighbor);
					if (neighbor.isWalkable()) {
						// add the neighbor to the queue and visited list
						visited.add(neighbor);
					}
				}
			}
		}
		// return the list of visited nodes
		return visited;

	}

	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {

		// create an empty stack to store the nodes to visit
		ArrayList<Tile> stack = new ArrayList<>();
		// create an empty ArrayList to store the visited nodes
		ArrayList<Tile> visited = new ArrayList<>();
		// add the start node to the stack and visited list

		stack.add(0, s);
		visited.add(s);

		// loop while the stack is not empty
		while (!stack.isEmpty()) {
			// remove the top node from the stack
			Tile current = stack.remove(0);
			// loop over the neighbors of the current node
			for (Tile neighbor : current.neighbors) {
				// check if the neighbor has not been visited
				if (!(visited.contains(neighbor)) && neighbor.isWalkable()) {
					// add the neighbor to the stack and visited list
					stack.add(0, neighbor);
					visited.add(neighbor);
				}
			}
		}
		// return the list of visited nodes
		return visited;
	}
}