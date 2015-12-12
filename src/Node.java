import java.util.ArrayList;
import java.util.HashMap;

public class Node implements Comparable<Node> {
	public int id;
	public int xCoord;
	public int yCoord;
	public ArrayList<Node> adjList;
	public HashMap<Node,Integer> realadjList;
	public int mindist = Integer.MAX_VALUE;

	public int getId() {
		return this.id;
	}

	public int getxCoord() {
		return this.xCoord;
	}

	public int getyCoord() {
		return this.yCoord;
	}

	public void addEdge(Node another) {
		this.adjList.add(another);
	}

	public Node(int id, int x, int y) {
		this.id = id;
		this.xCoord = x;
		this.yCoord = y;
		this.adjList = new ArrayList<Node>();
		this.realadjList = new HashMap<Node,Integer>();
	}

	@Override
	public int compareTo(Node o) {
		return 0;
	}
}
