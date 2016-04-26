import java.util.*;

public class Wombats {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int numBalls = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= i; j++) {
				numBalls += j;
			}
		}
		//0
		//1 2
		// 3
		//4 5 6
		// 7 8
		//  9
		//10 11 12 13
		//  14 15 16
		//   17 18
		//    19
		//1->0 2->0 3->0
		//4->3 5->3 7->3
		Dinic dn = new Dinic(numBalls+2);
		int index = 0;
		for (int i = 1; i < N; i++) {
			//@ level i
			//index is first index.

			int cur = index;

			int nextIndex = index + i*(i+1)/2;
			int nextRowIndex = nextIndex+(i+1);
			for (int j = i; j >= 1; j--) {
				for (int k = 0; k < j; k++) {
					//connect nextIndex, nextIndex+1, and
					//nextRowIndex with cur
					dn.addEdge(nextIndex, cur, 10000000);
					dn.addEdge(nextIndex+1, cur, 10000000);
					dn.addEdge(nextRowIndex, cur, 10000000);
					nextIndex++;
					nextRowIndex++;
					cur++;
				}
				//Fix nextIndex
				nextIndex++;
			}
			index = cur;
		}
		int src = numBalls;
		int snk = numBalls+1;
		int cur = 0;
		int posSum = 0;
		for (int i = 1; i <= N; i++) {
			for (int j = i; j >= 1; j--) {
				for (int k = 0; k < j; k++) {
					int amt = sc.nextInt();
					if (amt >= 0) {
						dn.addEdge(src, cur, amt);
						posSum += amt;
					} else
						dn.addEdge(cur, snk, -amt);
					cur++;
				}
			}
		}
		int amt = dn.maxflow(src, snk);
		System.out.println(posSum - amt);
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