import java.util.*;

public class E {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();
		int[] numbers = new int[n];
		ArrayList[] decomp = new ArrayList[n];
		int[] sum = new int[n];


		int amt = 2 + n;
		for (int i = 0; i < n; i++) {
			numbers[i] = sc.nextInt();
			decomp[i] = getPrimes(numbers[i]);
			amt += decomp[i].size();
			if (i != 0) {
				sum[i] += sum[i-1];
			}
			sum[i] += decomp[i].size();
		}

		Dinic mf = new Dinic(amt);
		int src = amt-2;
		int snk = amt-1;
		for (int i = 0; i < n; i++) {
			if ((i+1)%2 != 0) {
				//odd
				mf.addEdge(src, i, 100);
			} else {
				//even
				mf.addEdge(i, snk, 100);
			}

			ArrayList<Pair> dec = (ArrayList<Pair>) decomp[i];
			for (int j = 0; j < dec.size(); j++) {
				Pair p = dec.get(j);
				int pIdx = n;
				if (i != 0) {
					pIdx += sum[i-1];
				}
				pIdx += j;
				if ((i+1)%2 != 0) {
					mf.addEdge(i, pIdx, p.amt);
				} else {
					mf.addEdge(pIdx, i, p.amt);
				}
			}
		}

		for (int i = 0; i < m; i++) {
			int u = sc.nextInt(), v = sc.nextInt();
			if (u % 2 == 0) {
				int temp = u;
				u = v;
				v = temp;
			}
			ArrayList<Pair> pu = (ArrayList<Pair>) decomp[u-1];
			ArrayList<Pair> pv = (ArrayList<Pair>) decomp[v-1];
			//pu is odd
			//pv is even
			int uidx = 0;
			int vidx = 0;

			while (uidx < pu.size() && vidx < pv.size()) {
				Pair uPair = pu.get(uidx);
				Pair vPair = pv.get(vidx);
				if (uPair.num == vPair.num) {
					int uPairIdx = n;
					if (u != 1) {
						uPairIdx += sum[u-2];
					}
					uPairIdx += uidx;

					int vPairIdx = n;
					if (v != 1) {
						vPairIdx += sum[v-2];
					}
					vPairIdx += vidx;

					mf.addEdge(uPairIdx, vPairIdx, 100);

					uidx++;
					vidx++;
				} else if (uPair.num < vPair.num) {
					uidx++;
				} else {
					vidx++;
				}
			}
		}

		System.out.println(mf.maxflow(src, snk));
	}

	static ArrayList<Pair> getPrimes(int n) {
		ArrayList<Pair> primes = new ArrayList<Pair>();
		for (int i = 2; i * i <= n; i++) {
			int cnt = 0;
			while (n % i == 0) {
				n /= i;
				cnt++;
			}
			if (cnt != 0) {
				primes.add(new Pair(i, cnt));
			}
		}
		if (n != 1) {
			primes.add(new Pair(n, 1));
		}
		return primes;
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
class Pair {
	int num;
	int amt;
	Pair(int num, int amt) {
		this.num = num;
		this.amt = amt;
	}
	public String toString() {
		return "(" + num + "," + amt + ")";
	}
}