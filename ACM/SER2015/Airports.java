import java.util.*;

public class Airports {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();

		int[] inspect = new int[N];
		for (int i = 0; i < N; i++) {
			inspect[i] = sc.nextInt();
		}

		int[][] time = new int[N][N];
		int[][] min_cost = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				time[i][j] = sc.nextInt();
				min_cost[i][j] = time[i][j] + inspect[j];
				if (i == j) min_cost[i][j] = 0;
			}
		}	

		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					min_cost[i][j] = Math.min(
						min_cost[i][j],
						min_cost[i][k] + min_cost[k][j]
					);
				}
			}
		}
		Sat[] sats = new Sat[M];
		for (int i = 0; i < M; i++) {
			int from = sc.nextInt()-1;
			int to = sc.nextInt()-1;
			int start_time = sc.nextInt();
			sats[i] = new Sat(from, to, start_time);
		}

		Dinic maxflow = new Dinic(2*(M+3));
		int src = 0;
		int snk = 1;
		//m takes on [m, 2*m]
		//m starts from 2
		for (int i = 0; i < M; i++) {
			Sat sat = sats[i];

			int idx_one = i+2;
			int idx_two = idx_one+M;

			maxflow.addEdge(src, idx_one, 1);
			//maxflow.addEdge(idx_one, idx_two, 1);
			maxflow.addEdge(idx_two, snk, 1);
		}

		for (int i = 0; i < M; i++) {
			Sat ii = sats[i];
			for (int j = 0; j < M; j++) {
				if (i == j) continue;
				int end_one = i+2;
				int start_two = (j+2+M);
				int timeAtEndOne = ii.start_time+time[ii.from][ii.to] + inspect[ii.to];
				int timeAtStartTwo = timeAtEndOne+min_cost[ii.to][sats[j].from];
				if (timeAtStartTwo <= sats[j].start_time) {
					//we can redirect flight from i's end to j's in appropriate time
					maxflow.addEdge(end_one, start_two, 1);
					//System.out.println("CONN " + i + "," + j);
				}
			}
		}

		//src connects to all m
		//2*m connects to all sink
		int mf = maxflow.maxflow(src, snk);
		System.out.println((M-mf));
		//System.out.println("MF: " + mf);
		//output is m-mf?
	}
}
class Sat {
	int from, to;
	int start_time;
	public Sat(int from, int to, int start_time) {
		this.from = from;
		this.to = to;
		this.start_time = start_time;
	}
}
class Dinic {
 
	class Pair {
		public int capacity, flow, to, usedIdx;
		public Pair back;
	}

	public int N;// num nodes
	public LinkedList[] adjList;
	public int[] pos;
	public Queue<Integer> q = new LinkedList<Integer>();
	public int[] dist;

	// N is the number of nodes...labeled 0...N-1
	Dinic(int N) {
		this.N = N;
		this.adjList = new LinkedList[N];
	}

	void addEdge(int u, int v, int capacity) {
		//be careful copying this
		Pair one = new Pair();
		one.to = v;
		one.capacity = capacity;
		Pair back = new Pair();
		back.to = u;
		// back.capacity = capacity; (if bidirectional, uncomment this)
		one.back = back;
		back.back = one;
		if (adjList[u] == null) {
			adjList[u] = new LinkedList<Pair>();
		}
		if (adjList[v] == null) {
			adjList[v] = new LinkedList<Pair>();
		}
		adjList[u].add(one);
		adjList[v].add(back);
	}

	int maxflow(int source, int sink) {
		int maxflow = 0;// standard FF...
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