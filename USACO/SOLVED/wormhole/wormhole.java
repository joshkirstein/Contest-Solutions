/*
ID: joshkir1
LANG: JAVA
TASK: wormhole
*/
import java.io.*;
import java.util.*;

class wormhole
{
	static int N,cnt;
	static Point[] pts;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("wormhole.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
		N = sc.nextInt();
		pts = new Point[N];
		for (int i = 0; i < N; i++) pts[i] = new Point(sc.nextInt(), sc.nextInt());
		boolean[] taken = new boolean[N];
		int[] fill = new int[N];
		Arrays.fill(fill, -1);
		dfs(0, taken, fill);
		out.println(cnt);
		out.close();
		System.exit(0);
	}
	public static void dfs(int x, boolean[] taken, int[] pair) {
		if (x == taken.length-1) {
			boolean works = true;
			//System.out.println("SUP: " + Arrays.toString(pair));
			MAIN:
			for (int i = 0; i < taken.length; i++) {
				//start at i.
				boolean[][] visited = new boolean[taken.length][2];
				int cur = i;
				int type = 0;
				while (cur != -1) {
					if (visited[cur][type]) {
						works = false;
						break MAIN;
					}
					visited[cur][type] = true;
					if (type == 0) {//going in
						cur = pair[cur];
						type = 1;
					} else {//coming out
						//coming out of cur...
						Point curPt = pts[cur];
						int next = -1;
						for (int j = 0; j < pts.length; j++) {
							if (j != cur) {
								if (pts[j].y == curPt.y) {
									if (pts[j].x > curPt.x) {
										if (next == -1) {
											next = j;
										} else {
											if (pts[j].x < pts[next].x) {
												next = j;
											}
										}
									}
								}
							}
						}
						cur = next;
						type = 0;
					}
				}
			}

			if (!works) cnt++;
			return;
		}
		//System.out.println("X: " + x);
		//System.out.println(Arrays.toString(taken));
		if (taken[x]) {
			dfs(x+1, taken, pair);
			return;
		}
		taken[x] = true;
		for (int i = x+1; i < taken.length; i++) {
			if (!taken[i]) {
				//System.out.println("TOOK I: " + i + " BY: " + x);
				taken[i] = true;
				pair[x] = i;
				pair[i] = x;
				dfs(x+1, taken, pair);
				taken[i] = false;
			}
		}
		taken[x] = false;
	}
}
class Point {
	public int x, y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}