/*
ID: joshkir1
LANG: JAVA
TASK: ditch
*/
import java.io.*;
import java.util.*;

class ditch
{
	//uses edmond's karp for max flow
	static int[][] capacity;
	static int[][] residual;
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
		int sink = nodes-1;
		int mf = 0;
		while (true) {
			int[] pred = new int[nodes];
			int[] mincap = new int[nodes];
			Arrays.fill(pred, -2);
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(source);
			pred[source] = -1;
			mincap[source] = Integer.MAX_VALUE;
			while (!q.isEmpty()) {
				int poll = q.poll();
				if (poll == sink) break;
				for (int i = 0; i < nodes; i++) {
					if (pred[i] == -2) { // Not seen
						if (residual[poll][i] > 0) { // stuff left here
							pred[i] = poll;
							mincap[i] = Math.min(mincap[poll], residual[poll][i]);
							q.add(i);
						}
					}
				}
			}
			if (pred[sink] == -2) break;
			int bottleneck = mincap[sink];
			int cur = sink;
			while (true) {
				if (cur == source) break;
				int prev = pred[cur];
				residual[prev][cur] -= bottleneck;
				residual[cur][prev] += bottleneck;
				cur = prev;
			}
			mf += bottleneck;
		}
		out.println(mf);
		out.close();
		System.exit(0);
	}
}