import java.util.ArrayList;
import java.util.HashMap;

public class Node implements Comparable<Node> {
	public int id; // how to identify node
	public int xCoord;
	public int yCoord;
	public HashMap<Node, Double> neighbors; // map of neighbors and how far they
											// are
	public double dist = Double.MAX_VALUE;
	public Node prev = null; // who the previous node is in the shortest path to
								// this node

	public Node(int id, int x, int y) {
		this.id = id;
		this.xCoord = x;
		this.yCoord = y;
		this.neighbors = new HashMap<Node, Double>();
	}

	@Override
	public int compareTo(Node o) {
		return Double.compare(this.dist, o.dist);
	}
}
