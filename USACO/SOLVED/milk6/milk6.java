/*
ID: joshkir1
LANG: JAVA
TASK: milk6
*/
import java.io.*;
import java.util.*;

class milk6
{
	static PrintWriter out;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("milk6.in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter("milk6.out")));
		int nodes = sc.nextInt();
		int edges = sc.nextInt();
		Dinic mf = new Dinic(nodes+1, edges+1);
		for (int i = 1; i <= edges; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int cost = 1001*sc.nextInt()+1;
			mf.addEdge(from, to, cost, i);
		}
		long maxflow = mf.maxflow(1, nodes);
		int size = mf.sizeCut();
		System.out.println("MF: " + maxflow + " SIZE: "+ size);
		int min = -1;
		for (int i = edges; i >= 1; i--) {
			//mf.reset();
			int temp = mf.lookup[i].capacity;
			mf.lookup[i].capacity = 0;
			long flow = mf.maxflow(1, nodes);
			//System.out.println("TEST: " + i + " FLOW: " + flow + " TEMP " + temp);
			if (flow == 0 && temp == maxflow) {
				min = i;
			}
			if (flow >= maxflow) {
				//not required!
				System.out.println("BAD: " + i);
				continue;
			} else {
				mf.lookup[i].capacity = temp;
			}
		}
		mf.reset();
		maxflow = mf.maxflow(1, nodes);
		System.out.println("OUT: " + maxflow + " ");

		mf.bfs(maxflow, min);
		milk6.out.close();
		System.exit(0);
	}
}
class Dinic {
 
	class Pair {
		public int capacity, flow, to, usedIdx;
		public Pair back;
		public int id;
	}

	public int N;// num nodes
	public LinkedList[] adjList;
	public int[] pos;
	public Queue<Integer> q = new LinkedList<Integer>();
	public int[] dist;
	public Pair[] lookup;

	// N is the number of nodes...labeled 0...N-1
	Dinic(int N, int edges) {
		this.N = N;
		this.adjList = new LinkedList[N];
		this.lookup = new Pair[edges+1];
	}

	void reset() { 
		for (Pair p : lookup) {
			if (p == null) continue;
			p.flow = 0;
			p.back.flow = 0;
		}
	}

	void addEdge(int u, int v, int capacity, int id) {
		//be careful copying this
		Pair one = new Pair();
		one.to = v;
		one.capacity = capacity;
		one.id = id;
		Pair back = new Pair();
		back.to = u;
		back.id = id;
		//back.capacity = capacity; // (if bidirectional, uncomment this)
		one.back = back;
		back.back = one;
		lookup[id] = one;
		if (adjList[u] == null) {
			adjList[u] = new LinkedList<Pair>();
		}
		if (adjList[v] == null) {
			adjList[v] = new LinkedList<Pair>();
		}
		adjList[u].add(one);
		adjList[v].add(back);
	}

	long maxflow(int source, int sink) {
		long maxflow = 0;// standard FF...
		pos = new int[N];
		while (true) {
			if (!getLevel(source, sink)) break;
			Arrays.fill(pos, 0);//to stop bad paths
			while (true) {
				int flow = getBlockingFlow(source, sink, Integer.MAX_VALUE);
				if (flow == 0) {
					break;
				}
				maxflow += flow;
			}
		}
		return maxflow;
	}

	int getBlockingFlow(int idx, int sink, int curFlow) {// dfs
		if (idx == sink) {
			return curFlow;
		}
		if (adjList[idx] == null) return 0;
		for (int i = pos[idx]; i < adjList[idx].size(); i++, pos[idx]++) {
			Pair adj = (Pair) adjList[idx].get(i);
			if (dist[adj.to] == dist[idx] + 1 && adj.flow < adj.capacity) {
				int retFlow = getBlockingFlow(adj.to, sink,
					 Math.min(curFlow, adj.capacity - adj.flow));
					 
				if (retFlow > 0) {
					adj.flow += retFlow;
					adj.back.flow -= retFlow;
					return retFlow;
				}
			}
		}
		return 0;
	}

	void bfs(long maxflow, int min) {
		boolean[] marked = new boolean[N];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(1);
		marked[1] = true;	
		while (!q.isEmpty()) {
			int pop = q.poll();
			if (adjList[pop] == null) continue;
			for (int i = 0; i < adjList[pop].size(); i++) {
				Pair adj = (Pair) adjList[pop].get(i);
				if (adj.capacity - adj.flow > 0
						&& !marked[adj.to]) {
					marked[adj.to] = true;
					q.add(adj.to);
				}
			}
		}

		TreeSet<Integer> set = new TreeSet<Integer>();
		for (Pair p : lookup) {
			if (p == null || p.flow <= 0) continue;
			if (marked[p.back.to] && !marked[p.to]) set.add(p.id);
			if (marked[p.to] && !marked[p.back.to]) set.add(p.id);
		}
		System.out.println(Arrays.toString(marked));
		if (((maxflow-set.size())/1001) == 0) set = new TreeSet<Integer>();
		if (min != -1) {
			set = new TreeSet<Integer>();
			set.add(min);
		}
		milk6.out.println(((maxflow-set.size())/1001)+ " " + set.size());

		for (Integer i : set) milk6.out.println(i);
	}
	TreeSet<Integer> out;
	int sizeCut() {
		boolean[] marked = new boolean[N];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(1);
		marked[1] = true;	
		while (!q.isEmpty()) {
			int pop = q.poll();
			if (adjList[pop] == null) continue;
			for (int i = 0; i < adjList[pop].size(); i++) {
				Pair adj = (Pair) adjList[pop].get(i);
				if (adj.capacity - adj.flow > 0
						&& !marked[adj.to]) {
					marked[adj.to] = true;
					q.add(adj.to);
				}
			}
		}

		TreeSet<Integer> set = new TreeSet<Integer>();
		for (Pair p : lookup) {
			if (p == null || p.capacity <= 0) continue;
			if (marked[p.back.to] && !marked[p.to]) set.add(p.id);
			if (marked[p.to] && !marked[p.back.to]) set.add(p.id);
		}
		out = set;
		return set.size();
	}

	boolean getLevel(int source, int sink) {
		q.clear();
		q.add(source);
		if (dist == null) {
			dist = new int[N];
		}
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[source] = 0;
		while (!q.isEmpty()) {
			int pop = q.poll();
			if (adjList[pop] == null) continue;
			for (int i = 0; i < adjList[pop].size(); i++) {
				Pair adj = (Pair) adjList[pop].get(i);
				if (adj.capacity - adj.flow > 0
						&& dist[adj.to] == Integer.MAX_VALUE) {
					dist[adj.to] = dist[pop] + 1;
					q.add(adj.to);
				}
			}
		}
		return !(dist[sink] == Integer.MAX_VALUE);
	}
}