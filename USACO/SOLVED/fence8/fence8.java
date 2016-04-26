 /*
ID: joshkir1
LANG: JAVA
TASK: fence8
*/
import java.io.*;
import java.util.*;

class fence8
{
	static int N, R;
	static int[] buckets, items;
	static int[] itemsum;
	static int maxboard;
	static int totalBoards;
	static int maxrail;
	public static void main(String[] args) throws IOException
	{
		//long totalMs = System.currentTimeMillis();
		BufferedReader sc = new BufferedReader(new FileReader("fence8.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence8.out")));

		N = Integer.parseInt(sc.readLine());
		buckets = new int[N];
		int sumB = 0;
		for (int i = 0; i < N; i++) {
			buckets[i] = Integer.parseInt(sc.readLine());
			totalBoards += buckets[i];
			maxboard = Math.max(maxboard, buckets[i]);
			buckets[i] = -buckets[i];
		}
		Arrays.sort(buckets);
		for (int i = 0; i < N; i++) buckets[i] = -buckets[i];
		R = Integer.parseInt(sc.readLine());
		items = new int[R];
		int sumI = 0;
		int maxIdx = R-1;
		for (int i = 0; i < R; i++) {
			items[i] = Integer.parseInt(sc.readLine());
			maxrail = Math.max(maxrail, items[i]);
		}
		/*int[] tempItems = new int[maxIdx+1];
		for (int i = 0; i < tempItems.length; i++) {
			tempItems[i] = items[i];
		}
		items = tempItems;*/
		itemsum = new int[items.length];
		Arrays.sort(items);
		for (int i = 0; i < items.length; i++) {
			itemsum[i] = (i > 0 ? itemsum[i-1] : 0) + items[i];
		}
		int min = 0;
		int max = items.length-1;
		idx = new int[items.length];
		while (min < max) {
			System.out.println(min + " " + max);
			int i = (min+max+1)/2;
			waste = 0;
			totalItems = itemsum[i];
			waste = totalBoards-totalItems;
			boolean res = false;

			if (totalItems > totalBoards) {
			} else
				maxx = -1;
				res = DFS(0, i, 0);
				System.out.println(i + " DIDNT WORK? : " + res);
			if (!res) {
				max = i-1;
			} else {
				min = Math.max(i, maxx-1);
			}
		}
		//System.out.println("TOOK: " + (msSum) + " ms");
		//System.out.println("TOTAL: " + (System.currentTimeMillis() - totalMs));
		out.println(min == 0 ? 0 : min+1);
		out.close();
		System.exit(0);
	}

static int totalItems;
static int[] idx;
	static long msSum = 0;
	static int waste;
	static int maxx;

	public static boolean DFS(int depth, int depthLimit, int badwaste) {
		//System.out.println(badwaste);
		if (depth >= items.length) return true;
		if (depth > depthLimit) {
			maxx = Math.max(maxx, depth);
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] >= items[depth]) {
					buckets[i] -= items[depth];
					DFS(depth+1, depthLimit, badwaste);
					buckets[i] += items[depth];
					break;
				}
			}
			return true;
		}
		if (badwaste > waste) return false;
		boolean works = false;
		int cost = items[depthLimit-depth];
		int hold = 0;
		//boolean stop = false;
		if (depth > 0 && items[depthLimit-depth] == items[depthLimit-depth+1]) hold = idx[depth-1];
		for (int i = hold; i < buckets.length && !works; i++) {
			if (buckets[i] == cost) {
				buckets[i] = 0;
				idx[depth] = i;
				totalItems -= cost;
				works |= DFS(depth+1, depthLimit, (buckets[i] < items[0] ? badwaste+buckets[i] : badwaste));
				buckets[i] = cost;
				totalItems += cost;
				return works;
			}
		}
		boolean[] used = new boolean[maxboard+1];
		for (int i = hold; i < buckets.length && !works; i++) {
			if (used[buckets[i]] || buckets[i] < cost) continue;
			used[buckets[i]] = true;
			idx[depth] = i;
			buckets[i] -= cost;
			totalItems -=cost;
			works |= DFS(depth+1, depthLimit, (buckets[i] < items[0] ? badwaste+buckets[i] : badwaste));
			buckets[i] += cost;
			totalItems+=cost;
			if (buckets[i] - cost > maxrail) break;
		}
		return works;
	}
}