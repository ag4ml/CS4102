import java.util.ArrayList;
import java.util.HashMap;

public class Node implements Comparable<Node> {
	public int id;  // how to identify node
	public int xCoord;
	public int yCoord;
	public ArrayList<Node> adjList;
	public HashMap<Node,Integer> neighbors; // map of neighbors and how far they are
	public int dist = Integer.MAX_VALUE;
	public Node prev = null; //who the previous node is in the shortest path to this node

	public Node(int id, int x, int y) {
		this.id = id;
		this.xCoord = x;
		this.yCoord = y;
		this.adjList = new ArrayList<Node>();
		this.neighbors = new HashMap<Node,Integer>();
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.dist, o.dist);
	}
}
