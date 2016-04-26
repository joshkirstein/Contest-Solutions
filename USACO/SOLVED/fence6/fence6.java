/*
ID: joshkir1
LANG: JAVA
TASK: fence6
*/
import java.io.*;
import java.util.*;

class fence6
{
	static Edge[] edgeList;
	static int N;
	static ArrayList<Node> graph;
	//Idea: Find smallest cycle?
	//Use dijkstra from each vertex, find min cost to get back to itself. Report min out of all of these?

	//analysis suggests another way to find cycle: delete each edge and find the shortest path
	//between it's two endpoints (then add the edge cost) and find the min of all these values.
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("fence6.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence6.out")));
		N = sc.nextInt();
		edgeList = new Edge[N+1];
		for (int i = 0; i < N; i++) {
			int ID = sc.nextInt();
			int len = sc.nextInt();
			int N1 = sc.nextInt();
			int N2 = sc.nextInt();
			int[] left = new int[N1];
			int[] right = new int[N2];
			for (int j = 0; j < N1; j++) left[j] = sc.nextInt();
			for (int j = 0; j < N2; j++) right[j] = sc.nextInt();
			edgeList[ID] = new Edge(ID, len, left, right);
		}
		graph = new ArrayList<Node>();
		for (int i = 1; i <= N; i++) {
			Edge edge = edgeList[i];
			Node left=null;
			Node right=null;
			boolean add = true;
			boolean add2 = true;
			for (Node n : graph) {
				if (n.equals(i, true)) {
					left = n;
					add = false;
				}
				if (n.equals(i, false)) {
					right = n;
					add2 = false;
				}
			}
			if (left == null) left = new Node(i, true);
			if (right == null) right = new Node(i, false);
			left.addEdge(right, edge.len);
			right.addEdge(left, edge.len);
			if (add)
				graph.add(left);
			if (add2)
				graph.add(right);
		}
		int min = Integer.MAX_VALUE;
		for (Node n : graph) {
			System.out.println("NODE: " + n.magicid + " -> " + n.adjList + " -> " + dijkstra(n));
			min = Math.min(min, dijkstra(n));
		}
		out.println(min);
		out.close();
		System.exit(0);
	}

	public static int dijkstra(Node start) {
		HashMap<Node, Integer> dist = new HashMap<Node, Integer>();
		HashMap<Node, Node> pred = new HashMap<Node, Node>();
		dist.put(start, 0);
		PriorityQueue<State> pq = new PriorityQueue<State>();
		pq.add(new State(start, 0, null));
		pred.put(start, null);
		while (!pq.isEmpty()) {
			State poll = pq.poll();
			Node node = poll.node;
			Node from = poll.from;
			int dis = poll.dist;
			if (dis > dist.get(node)) continue;
			for (Adj adj : node.adjList) {
				if (from == null || !adj.node.equals(from)) {
					if (adj.node.equals(start)) {
						//min = Math.min(min, dis + adj.length);
					} else {
						if (!dist.containsKey(adj.node)) {
							Node toNode = adj.node;
							dist.put(toNode, dis+adj.length);
							pq.add(new State(toNode, dis+adj.length, node));
							pred.put(toNode, node);
						} else {
							int old = dist.get(adj.node);
							if (old > (dis+adj.length)) {
								Node toNode = adj.node;
								dist.put(toNode, dis+adj.length);
								pq.add(new State(toNode, dis+adj.length, node));
								pred.put(toNode, node);
							}
						}
					}
				}
			}
		}
		int min = Integer.MAX_VALUE;
		for (Node n : graph) {
			if (n.equals(start)) continue;
			MAIN:
			for (Node m : graph) {
				if (m.equals(start)) continue;
				if (n != m) {
					if (n.isAdjacentTo(m)) {
						int length = n.get(m);
						int distToN = dist.get(n);
						int distToM = dist.get(m);
						int total = length+distToN+distToM;
						ArrayList<Node> npath = new ArrayList<Node>();
						Node cur = n;
						npath.add(cur);
						while (true) {
							Node predh = pred.get(cur);
							if (predh.equals(start)) break;
							npath.add(predh);
							cur = predh;
						}

						cur = m;
						while (true) {
							if (npath.contains(cur)) {
								continue MAIN;
							}
							Node pred2 = pred.get(cur);
							if (pred2.equals(start)) break;
							cur = pred2;
						}

						min = Math.min(min, total);
					}
				}
			}
		}
		return min;
	}
}
class State implements Comparable<State> {
	public Node node, from;
	public int dist;
	public State(Node n, int d, Node f) {
		node = n;
		dist = d;
		from = f;
	}
	public int compareTo(State s) {
		return dist-s.dist;
	}
}
class Adj {
	Node node;
	int length;
	public Adj(Node n, int len) {
		node = n;
		length = len;
	}
	public String toString() {
		return "(" + node.magicid + " , " + length + ")";
	}
}
class Node {
	static int IDS = 0;
	ArrayList<Adj> adjList;
	int id;
	boolean left;
	Edge edge;
	int magicid;
	public Node(int id, boolean left) {
		this.id = id;
		this.left = left;
		this.edge = fence6.edgeList[id];
		this.adjList = new ArrayList<Adj>();
		magicid = IDS++;
	}
	public int get(Node other) {
		for (Adj a : adjList) {
			if (a.node.equals(other)) return a.length;
		}
		return -1;	
	}
	public boolean isAdjacentTo(Node other) {
		for (Adj a : adjList) {
			if (a.node.equals(other)) return true;
		}
		return false;
	}
	public void addEdge(Node next, int cost) {
		adjList.add(new Adj(next, cost));
	}
	public boolean contains(int[] list, int val) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == val) return true;
		}
		return false;
	}
	public boolean equals(int fenceID, boolean left) {
		int id1 = id;
		boolean left1 = this.left;
		int id2 = fenceID;
		boolean left2 = left;
		int[] list1 = (left1 ? edge.left : edge.right);
		int[] list2 = (left2 ? fence6.edgeList[id2].left : fence6.edgeList[id2].right);
		return contains(list1, id2) && contains(list2, id1);
	}
	public boolean equals(Object o) {
		Node n = (Node) o;
		return magicid == n.magicid;
	}
	public int hashCode() {return magicid;}
}
class Edge {
	public int ID;
	public int len;
	public int[] left;
	public int[] right;
	public Edge(int ID, int len, int[] left, int[] right) {
		this.ID = ID;
		this.len = len;
		this.left = left;
		this.right = right;
	}
}