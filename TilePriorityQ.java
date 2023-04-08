package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	private ArrayList<Tile> minHeap;
	// TODO level 3: implement the constructor for the priority queue

	public TilePriorityQ (ArrayList<Tile> vertices) {
		minHeap = new ArrayList<>(vertices);
		buildMinHeap();
//		for (int i = 0; i < minHeap.size(); i++) {
//			for (int j = 0; j < minHeap.size(); j++) {
//				if (minHeap.get(j).distanceCost < minHeap.get(i).distanceCost) {
//					Tile temp = minHeap.get(i);
//					minHeap.set(i , minHeap.get(j));
//					minHeap.set(j, temp);
//				}
//			}
//		}
	}

	private void buildMinHeap() {
		// Start from the middle of the list and work backwards
		for (int i = minHeap.size() / 2 - 1; i >= 0; i--) {
			minHeapify(i);
		}
	}

	private void minHeapify(int index) {
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		int smallest = index;

		// Find the smallest element among the current node and its children
		if (left < minHeap.size() && minHeap.get(left).costEstimate < minHeap.get(smallest).costEstimate) {
			smallest = left;
		}
		if (right < minHeap.size() && minHeap.get(right).costEstimate < minHeap.get(smallest).costEstimate) {
			smallest = right;
		}

		// Swap the smallest element with the current node if necessary
		if (smallest != index) {
			Tile temp = minHeap.get(index);
			minHeap.set(index, minHeap.get(smallest));
			minHeap.set(smallest, temp);
			minHeapify(smallest);
		}
	}

	public void add(Tile vertex) {
		minHeap.add(vertex);
		int index = minHeap.size() - 1;

		// Swap the new element with its parent until it is in the correct position
		while (index > 0) {
			int parent = (index - 1) / 2;
			if (minHeap.get(index).costEstimate < minHeap.get(parent).costEstimate) {
				Tile temp = minHeap.get(index);
				minHeap.set(index, minHeap.get(parent));
				minHeap.set(parent, temp);
				index = parent;
			} else {
				break;
			}
		}
	}

	public Tile poll() {

		if (isEmpty()) {
			return null;
		}
		if (minHeap.size() == 1) {
			return minHeap.remove(0);
		}

		// Remove the root of the min heap and replace it with the last element
		Tile root = minHeap.get(0);
		minHeap.set(0, minHeap.remove(minHeap.size() - 1));
		// Restore the min heap property
		minHeapify(0);
		return root;
	}

	public boolean isEmpty() {
		return minHeap.isEmpty();
	}


	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile lowest = minHeap.get(0);
		for (Tile t :
				minHeap) {
			if(t.costEstimate < lowest.costEstimate) {
				lowest = t;
			}
		}

		minHeap.remove(lowest);
//		Tile removed = minHeap.remove(0);
		return lowest;

		//return poll();
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {

//		t.predecessor = newPred;
//		t.costEstimate = newEstimate;
//		if (minHeap.contains(t)) {
//			for (int i = 0; i < minHeap.size(); i++) {
//				for (int j = 0; j < minHeap.size(); j++) {
//					if (minHeap.get(i).distanceCost > minHeap.get(j).distanceCost) {
//						Tile temp = minHeap.get(j);
//						minHeap.set(j , minHeap.get(i));
//						minHeap.set(i, temp);
//					}
//				}
//			}
//		}


		// Update the predecessor and estimated cost of the tile
		if (minHeap.contains(t)) {
			t.predecessor = newPred;
			t.costEstimate = newEstimate;


			// Find the index of the tile in the min heap
			int index = minHeap.indexOf(t);
			if (index == -1) {
				// The tile is not in the min heap, so we can't update its position
				return;
			}

			// Swap the tile with its parent until it is in the correct position
			while (index > 0) {
				int parent = (index - 1) / 2;
				if (minHeap.get(index).costEstimate < minHeap.get(parent).costEstimate) {
					Tile temp = minHeap.get(index);
					minHeap.set(index, minHeap.get(parent));
					minHeap.set(parent, temp);
					index = parent;
				} else {
					break;
				}
			}
			// Restore the min heap property
			minHeapify(index);
		}
	}
}