import java.util.ArrayList;


public class Node {
	private int id;
	private int xCoord;
	private int yCoord;
	ArrayList<Node> adjList;

	public int getId(){
		return this.id;
	}
	public int getxCoord(){
		return this.xCoord;
	}
	public int getyCoord(){
		return this.yCoord;
	}
	public void addEdge(Node another){
		this.adjList.add(another);
	}
	public Node(int id, int x, int y){
		this.id = id;
		this.xCoord = x;
		this.yCoord = y;
		this.adjList = new ArrayList<Node>();
	}
}
