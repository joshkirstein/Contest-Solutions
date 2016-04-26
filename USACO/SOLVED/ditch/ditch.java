/*
ID: joshkir1
LANG: JAVA
TASK: ditch
*/
import java.io.*;
import java.util.*;

class ditch
{
	// Uses dinic's algorithm for max flow
	static int[][] capacity;
	static int[][] residual;
	static int sink;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("ditch.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));
		int edges = sc.nextInt();
		int nodes = sc.nextInt();
		capacity = new int[nodes][nodes];
		residual = new int[nodes][nodes];
		for (int i = 0; i < edges; i++) {
			int from = sc.nextInt()-1;
			int to = sc.nextInt()-1;
			int cap = sc.nextInt();
			capacity[from][to] += cap;
			residual[from][to] += cap;
		}
		int source = 0;
		sink = nodes-1;
		int mf = 0;
		while (true) {
			int[] pred = new int[nodes];
			int[] mincap = new int[nodes];
			int[] dist = new int[nodes];
			Arrays.fill(pred, -2);
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(source);
			pred[source] = -1;
			mincap[source] = Integer.MAX_VALUE;
			dist[source] = 0;
			while (!q.isEmpty()) {
				int poll = q.poll();
				if (poll == sink) break;
				for (int i = 0; i < nodes; i++) {
					if (pred[i] == -2) { // Not seen
						if (residual[poll][i] > 0) { // stuff left here
							pred[i] = poll;
							dist[i] = dist[poll]+1;
							mincap[i] = Math.min(mincap[poll], residual[poll][i]);
							q.add(i);
						}
					}
				}
			}
			if (pred[sink] == -2) break;
			while (true) {
				int bottleneck = getBF(source, dist, Integer.MAX_VALUE);
				if (bottleneck == Integer.MAX_VALUE) break;
				mf += bottleneck;
			}
		}
		out.println(mf);
		out.close();
		System.exit(0);
	}

	public static int getBF(int cur, int[] dist, int min) {
		if (cur == sink) return min;
		for (int i = 0; i < residual.length; i++) {
			if (residual[cur][i] > 0 && dist[i] == dist[cur]+1) {
				int res = getBF(i, dist, Math.min(min, residual[cur][i]));
				if (res == Integer.MAX_VALUE) continue;
				residual[cur][i] -= res;
				residual[i][cur] += res;
				return res;
			}
		}
		return Integer.MAX_VALUE;
	}
}