import java.util.*;
import static java.lang.Math.*;

public class Containment {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int amt = sc.nextInt();

		while (amt-->0) {
		MaxFlow.reset();
		Label sourceLabel = Label.makeLabel(-1, -1, -1, -1);
		Label sinkLabel = Label.makeLabel(-1, -1, -1, -2);


		String sl = sourceLabel.str;//srclabel
		String sil = sinkLabel.str;//sinklabel

		//Connect each edge to its neighbour with just two edges with cap 1
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 9; y++) {
				for (int z = 0; z <= 9; z++) {

					Label from = Label.makeLabel(x, y, z, 1);
				//	if (x-1 >= 0) {
					Label l1 = Label.makeLabel(x-1, y, z, 1);

					MaxFlow.addEdgeUnique(from.str, l1.str, 1);//}

					//if (x+1 <= 9) {
					Label l2 = Label.makeLabel(x+1, y, z, 1);
					MaxFlow.addEdgeUnique(from.str, l2.str, 1);
				//}
				//	if (y-1 >= 0) {
					Label l3 = Label.makeLabel(x, y-1, z, 1);

					MaxFlow.addEdgeUnique(from.str, l3.str, 1);
				//}
				//if (y+1 <= 9) {
					Label l4 = Label.makeLabel(x, y+1, z, 1);
					MaxFlow.addEdgeUnique(from.str, l4.str, 1);

				//}
				//if (z-1 >= 0) {
					Label l5 = Label.makeLabel(x, y, z-1, 1);

					MaxFlow.addEdgeUnique(from.str, l5.str, 1);
				//}
	//			if (z+1 <= 9) {

					Label l6 = Label.makeLabel(x, y, z+1, 1);

					MaxFlow.addEdgeUnique(from.str, l6.str, 1);
			//	}

				}
			}
		}

		boolean[][][] bad = new boolean[10][10][10];
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			int x = sc.nextInt(), y = sc.nextInt(), z = sc.nextInt();
			bad[x][y][z] = true;
		}

		//bad stuff flows into bad[x][y][z] cells
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 9; y++) {
				for (int z = 0; z <= 9; z++) {
					if (bad[x][y][z]) {

						Label from = Label.makeLabel(x, y, z, 1);
						MaxFlow.addEdge(sl, from.str, 6);
					}

				}
			}
		}
		//sink connects to the edge cells (not real points)....
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 9; y++) {

				Label from = Label.makeLabel(x, y, -1, 1);
				MaxFlow.addEdge(from.str, sil, 1);
				Label from2 = Label.makeLabel(x, y, 10, 1);
				MaxFlow.addEdge(from2.str, sil, 1);
				Label from3 = Label.makeLabel(x, 10, y, 1);
				MaxFlow.addEdge(from3.str, sil, 1);
				Label from4 = Label.makeLabel(x, -1, y, 1);
				MaxFlow.addEdge(from4.str, sil, 1);

				Label from5 = Label.makeLabel(10, x, y, 1);
				MaxFlow.addEdge(from5.str, sil, 1);
				Label from6 = Label.makeLabel(-1, x, y, 1);
				MaxFlow.addEdge(from6.str, sil, 1);
			}
		}
		System.out.println( MaxFlow.mf(sl, sil) );
		}
	}
}
class Label {

	String str;
	int x, y, z, type;

	public static Label getLabel(String str) {
		String[] spl = str.split(" ");
		Label l = new Label();
		l.x = Integer.parseInt(spl[0]);
		l.y = Integer.parseInt(spl[1]);
		l.z = Integer.parseInt(spl[2]);
		l.type = Integer.parseInt(spl[3]);
		l.str = str;
		return l;
	}

	public static Label makeLabel(int x, int y, int z, int type) {
		Label l = new Label();
		l.x = x;
		l.y = y;
		l.z = z;
		l.type = type;
		l.str = x + " " + y + " " + z + " " + type;
		return l;
	}
}
class Edge {
	String from;
	String to;
	int capacity;
	int flow;
	Edge reverse;
}
class MaxFlow {
	static void reset() {
		adjList = new HashMap<String, ArrayList<Edge>>();
		visited = new HashSet<String>();
	}
	static HashMap<String, ArrayList<Edge>> adjList 
		= new HashMap<String, ArrayList<Edge>>();
	static HashSet<String> visited;
	static int mf(String src, String sink) {
		int mf = 0;
		while (true) {
			visited = new HashSet<String>();
			int augment = augment(src, 1000000, sink, src);
			//System.out.println("Augment: " + augment);
			if (augment == 0) break;
			mf += augment;
		}
		return mf;
	}

	static int augment(String cur, int toAug, final String sink, final String start) {
		if (cur.equals(sink)) {
			return toAug;
		}
		visited.add(cur);
		ArrayList<Edge> edges = adjList.get(cur);
		if (edges != null) {
			for (Edge e : edges) {
				String next = e.to;
				//if (next.equals(sink) && cur.equals(start)) continue;
				if (!visited.contains(next) && abs(e.capacity-e.flow) > 0) {
					int augment = augment(next, Math.min(toAug, abs(e.capacity-e.flow)), sink, start);
					if (augment != 0) {
						e.flow += augment;
						e.reverse.flow -= augment;
						return augment;
					}
				}
			}
		}
		return 0;
	}

	static void addEdgeUnique(String from, String to, int capacity) {
		ArrayList<Edge> edges = adjList.get(from);
		if (edges != null) {
			for (Edge e : edges) {
				if (e.to.equals(to)) return;
			}
		}
		Edge straight = new Edge();
		Edge reverse = new Edge();
		straight.from = from;
		straight.to = to;
		straight.capacity = capacity;
		straight.flow = 0;
		straight.reverse = reverse;
		reverse.from = to;
		reverse.to = from;
		reverse.capacity = capacity;
		reverse.flow = 0;
		reverse.reverse = straight;
		if (!adjList.containsKey(from)) adjList.put(from, new ArrayList<Edge>());
		if (!adjList.containsKey(to)) adjList.put(to, new ArrayList<Edge>());
		adjList.get(from).add(straight);
		adjList.get(to).add(reverse);
	}

	static void addEdge(String from, String to, int capacity) {
		Edge straight = new Edge();
		Edge reverse = new Edge();
		straight.from = from;
		straight.to = to;
		straight.capacity = capacity;
		straight.flow = 0;
		straight.reverse = reverse;
		reverse.from = to;
		reverse.to = from;
		reverse.capacity = 0;
		reverse.flow = 0;
		reverse.reverse = straight;
		if (!adjList.containsKey(from)) adjList.put(from, new ArrayList<Edge>());
		if (!adjList.containsKey(to)) adjList.put(to, new ArrayList<Edge>());
		adjList.get(from).add(straight);
		adjList.get(to).add(reverse);
	}
}