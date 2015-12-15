import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

public class Dijkstra {
	public static HashMap<String, Double> edges = new HashMap<>();
	public static ArrayList<Node> Graph = new ArrayList<Node>();
	public static int noNodes;
	public static int noEdges;
	public static HashMap<Node, FibonacciHeapNode<Node>> fibNodes;
	public static double[] dist;

	public static void main(String[] args) throws FileNotFoundException {
		/* read input from text file to set up graph */
		readInput("datasets/usa.txt");
		// displayGraph();
		// start with node 0
		Node source = Graph.get(2);
		Node target = Graph.get(31000);
		double t1 = timeDijkstra(source, target);
		
		double t2 = timeDijkstraFib(source, target);
	//	printPath(source, target, false);
		double t3 = timeAStar(source, target);

		printPath(source, target, true);
		System.out.println("Plain Dijkstra took: " + String.format("%.2f", t1/1000000)+ " milliseconds");
		System.out.println("Fibheap Dijkstra took: "+ String.format("%.2f", t2/1000000) + " milliseconds");
		System.out.println("A* took: " + String.format("%.2f", t3/1000000) + " milliseconds");
	}

	private static double timeAStar(Node source, Node target) {
		double start3 = System.nanoTime();
		for (int i = 0; i < 100; i += 1)runAstar(source, target);
		double end3 = System.nanoTime();
		return (end3 - start3) / 100;
	}

	private static double timeDijkstraFib(Node source, Node target) {
		double start2 = System.nanoTime();
		for (int i = 0; i < 100; i += 1) runDijkstraFibHeap(source, target);
		double end2 = System.nanoTime();
		return (end2 - start2) / 100;
	}

	private static double timeDijkstra(Node source, Node target) {
		double start1 = System.nanoTime();
		for (int i = 0; i < 100; i += 1) runDijkstra(source, target);
		double end1 = System.nanoTime();
		return (end1 - start1) / 100;
	}

	private static void initFibNodes() {
		fibNodes = new HashMap<>();
		for (Node n : Graph)
			fibNodes.put(n, new FibonacciHeapNode<Node>(n));
	}

	private static void runDijkstraFibHeap(Node source, Node target) {
		initGraph(source);
		initFibNodes();
		FibonacciHeap<Node> fibheap = new FibonacciHeap<Node>();
		for (Node n : Graph)
			fibheap.insert(fibNodes.get(n), n.dist);

		while (!fibheap.isEmpty()) {
			Node current = fibheap.removeMin().getData();
			if(current.id == target.id && target.dist!=Double.MAX_VALUE) return;
			for (Node neighbor : current.neighbors.keySet()) {
				double distviacurrent = current.dist
						+ current.neighbors.get(neighbor);
				if (distviacurrent < neighbor.dist) {
					neighbor.dist = distviacurrent;
					neighbor.prev = current;
					fibheap.decreaseKey(fibNodes.get(neighbor), neighbor.dist);
				}
			}

		}
	}

	private static void runDijkstra(Node source, Node target) {
		initGraph(source);
		PriorityQueue<Node> pq = new PriorityQueue<Node>(Graph.size());
		pq.add(source);

		while (!pq.isEmpty()) {
			Node current = pq.poll();
			if(current.id == target.id && target.dist!=Double.MAX_VALUE)return;
			for (Node neighbor : current.neighbors.keySet()) {
				double distviacurrent = current.dist
						+ current.neighbors.get(neighbor);
				if (distviacurrent < neighbor.dist) {
					if (pq.contains(neighbor))
						pq.remove(neighbor);
					neighbor.dist = distviacurrent;
					neighbor.prev = current;
					pq.add(neighbor);
				}
			}

		}

	}

	private static void runAstar(Node source, Node target) {
		initGraph(source);
		PriorityQueue<Node> pq = new PriorityQueue<Node>(Graph.size());
		pq.add(source);
		dist[source.id] = 0;

		while (!pq.isEmpty()) {
			Node current = pq.poll();
			if(current.id == target.id && target.dist!=Double.MAX_VALUE)return;
			for (Node neighbor : current.neighbors.keySet()) {
				double heuristic = heuristic(current, neighbor, target);
				double distviacurrent = dist[current.id]
						+ current.neighbors.get(neighbor);
				if (distviacurrent + heuristic < neighbor.dist) {
					if (pq.contains(neighbor))
						pq.remove(neighbor);
					neighbor.dist = distviacurrent + heuristic;
					dist[neighbor.id] = distviacurrent;
					neighbor.prev = current;
					pq.add(neighbor);
				}
			}

		}

	}

	private static double heuristic(Node current, Node neighbor, Node target) {
		return compDistance(neighbor, target);
	}

	private static void initGraph(Node source) {
		// init distance to infinity, except source (make that 0)
		for (Node n : Graph) {
			n.dist = (n.id == source.id) ? 0 : Double.MAX_VALUE;
			n.prev = (n.id == source.id) ? source : null;
		}
	}

	private static void printPath(Node source, Node target, boolean b) {
		if (b)
			for (Node n : Graph)
				n.dist = dist[n.id];
		String path = "" + target.id;
		int count = 0;
		Node prev = target.prev;
		while (prev != source) {
			path = prev.id + "->" + path;
			prev = prev.prev;
			count += 1;
		}
		path = source.id + "->" + path;
		count += 1;
		System.out.println(path);
		System.out.println("Path distance: " + String.format("%.2f", target.dist)
				+ " Edges Traversed: " + count);
	}

	private static void displayGraph() {
		System.out.println("Number of nodes: " + noNodes);
		System.out.println("Number of edges: " + noEdges);

		for (Node n : Graph)
			System.out.println(n.id + "  " + n.xCoord + "  " + n.yCoord);

		for (Node n : Graph) {
			for (Node x : n.neighbors.keySet()) {
				System.out.println(n.id + "  " + x.id + " "
						+ n.neighbors.get(x));
			}
		}

	}

	private static void readInput(String filename) throws FileNotFoundException {
		Scanner inputScanner = new Scanner(new File(filename));
		ArrayList<Integer> val = new ArrayList<Integer>();
		while (inputScanner.hasNextInt())
			val.add(inputScanner.nextInt());
		inputScanner.close();
		noNodes = val.get(0);
		noEdges = val.get(1);

		int i;
		for (i = 2; i < 2 + 3 * noNodes; i += 3) {
			int id = val.get(i);
			int xCoord = val.get(i + 1);
			int yCoord = val.get(i + 2);
			Graph.add(new Node(id, xCoord, yCoord));
		}
		int j;
		for (j = i; j < i + 2 * noEdges; j += 2) {
			Node node1 = Graph.get(val.get(j));
			Node node2 = Graph.get(val.get(j + 1));
			double distance = compDistance(node1, node2);
			node1.neighbors.put(node2, distance);
			node2.neighbors.put(node1, distance);
		}
		dist = new double[noNodes];
	}

	private static double compDistance(Node node1, Node node2) {
		return Math.sqrt(Math.pow(node2.xCoord - node1.xCoord, 2)
				+ Math.pow(node2.yCoord - node1.yCoord, 2));
	}
}
