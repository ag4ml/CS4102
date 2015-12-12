import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dijkstra {
	public static HashMap<String, Integer> edges = new HashMap<>();
	public static ArrayList<Node> Graph = new ArrayList<Node>();
	public static int noNodes;
	public static int noEdges;

	public static void main(String[] args) throws FileNotFoundException {
		/* read input from text file to set up graph */
		readInput("datasets/input6.txt");
		System.out.println("Hello world! I successfully read in data");
		transformDataSet();
		System.out.println("Hello world! I successfully set up graph. Running Dijkstra...");
		// start with node 0
		Node source = Graph.get(0);
		runDijkstra(source);
	}

	private static void transformDataSet() {
		for(Node n: Graph){
			for(Node adj: n.adjList){
				int edgeWeight = edges.get(""+n.id+adj.id);
				n.neighbors.put(adj, edgeWeight);
			}
		}
	}

	private static void runDijkstra(Node source) {

	}

	private static void readInput(String filename) throws FileNotFoundException {
		Scanner inputScanner = new Scanner(new File(filename));
		String[] vals = inputScanner.nextLine().split(" ");
		noNodes = Integer.parseInt(vals[0]);
		noEdges = Integer.parseInt(vals[1]);
		for (int i = 0; i < noNodes; i += 1) {
			String[] values = inputScanner.nextLine().split(" ");
			Graph.add(new Node(Integer.parseInt(values[0]), Integer
					.parseInt(values[1]), Integer.parseInt(values[2])));
		}
		inputScanner.nextLine();
		for (int j = 0; j < noEdges; j += 1) {
			String[] node = inputScanner.nextLine().split(" ");
			int[] nodes = new int[node.length];
			for (int i = 0; i < nodes.length; i += 1)
				nodes[i] = Integer.parseInt(node[i]);
			Node node1 = Graph.get(nodes[0]);
			Node node2 = Graph.get(nodes[1]);
			node1.adjList.add(node2);
			node2.adjList.add(node1);
			int distance = compDistance(node1, node2);
			String key1 = "" + node1.id + node2.id;
			String key2 = "" + node2.id + node1.id;
			edges.put(key1, distance);
			edges.put(key2, distance);
		}
	}

	private static int compDistance(Node node1, Node node2) {
		return (int) Math.sqrt(Math.pow(node2.xCoord - node1.xCoord,
				2) + Math.pow(node2.yCoord - node1.yCoord, 2));
	}
}
