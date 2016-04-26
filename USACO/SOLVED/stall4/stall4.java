/*
ID: joshkir1
LANG: JAVA
TASK: stall4
*/
import java.io.*;
import java.util.*;

class stall4
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("stall4.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stall4.out")));

		int cows = sc.nextInt();
		int stalls = sc.nextInt();
		int nodes = cows+stalls+2;
		int source = cows+stalls;
		int sink = cows+stalls+1;
		int[][] adjMat = new int[nodes][nodes];
		for (int i = 0; i < cows; i++) {
			int numStalls = sc.nextInt();
			for (int j = 0; j < numStalls; j++) {
				int stall = sc.nextInt()+cows-1;
				adjMat[i][stall] = 1;
			}
			adjMat[source][i] = 1;
		}
		for (int i = 0; i < stalls; i++) {
			int st = i+cows;
			adjMat[st][sink] = 1;
		}
		int mf = 0;
		while (true) {
			int[] min = new int[nodes];
			int[] pred = new int[nodes];
			Arrays.fill(pred, -2);
			Queue<Integer> q = new LinkedList<Integer>();
			pred[source] = -1;
			min[source] = Integer.MAX_VALUE;
			q.add(source);
			while (!q.isEmpty()) {
				int poll = q.poll();
				if (poll == sink) break;
				for (int i = 0; i < nodes; i++) {
					if (pred[i] == -2) {
						if (adjMat[poll][i] > 0) {
							pred[i] = poll;
							min[i] = Math.min(min[poll], adjMat[poll][i]);
							q.add(i);
						}
					}
				}
			}
			if (pred[sink] == -2) break;
			int bottleneck = min[sink];
			mf += bottleneck;
			int cur = sink;
			while (true) {
				if (cur == source) break;
				int prev = pred[cur];
				adjMat[prev][cur] -= bottleneck;
				adjMat[cur][prev] += bottleneck;
				cur = prev;
			}
		}
		out.println(mf);
		out.close();
		System.exit(0);
	}
}