import java.util.*;

public class Walls {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == 0) break;
			Pt[] pts = new Pt[N];
			int maxX = 0;
			ArrayList[] xs = new ArrayList[37];
			for (int i = 0; i <= 36; i++) {
				xs[i] = new ArrayList<Pt>();
			}
			for (int i = 0; i < N; i++) {
				pts[i] = new Pt();
				pts[i].x = sc.nextInt();
				pts[i].y = sc.nextInt();
				maxX = Math.max(maxX, pts[i].x);
				xs[pts[i].y].add(pts[i]);
			}
			for (ArrayList a : xs) Collections.sort(a);
			Arrays.sort(pts);
			int ans = Integer.MAX_VALUE;
			int pow = 1 << (maxX/2);
			MAIN:
			for (int bitmask = 0; bitmask < pow; bitmask++) {
				int cnt = 0;
				boolean[] wall = new boolean[37];
				int[] wallUpto = new int[37];

				for (int j = 0; j < maxX/2; j++) {
					if ((bitmask & (1 << j)) != 0) {
						wall[2*j+1] = true;
						cnt++;
					}
				}

				for (int i = 0; i < 37; i++) {
					if (wall[i]) wallUpto[i] = 1;
					if (i != 0) wallUpto[i] += wallUpto[i-1];
				}

				for (int i = 0; i < 37; i++) {
					ArrayList<Pt> ptz = (ArrayList<Pt>) xs[i];	
					for (int j = 0; j < ptz.size()-1; j++) {
						Pt p = ptz.get(j);
						Pt p2 = ptz.get(j+1);
						int from = p.x;
						int to = p2.x;
						if (wallUpto[to]-wallUpto[from] == 0) {
							continue MAIN;
						}
					}
				}

				int numBlocks = cnt;
				int place = 1;
				int[] inBlock = new int[numBlocks+1];

				for (int i = 0; i < 37; i++) {
					ArrayList<Pt> ptz = (ArrayList<Pt>) xs[i];
					boolean hasPrev = false;
					for (Pt p : ptz) {
						int blockid = wallUpto[p.x];
						hasPrev |= inBlock[blockid] == place;
					}
					if (hasPrev) {
						cnt++;
						place++;
						for (Pt p : ptz) {
							int blockid = wallUpto[p.x];
							inBlock[blockid] = place;
						}
					} else {
						for (Pt p : ptz) {
							int blockid = wallUpto[p.x];
							inBlock[blockid] = place;
						}
					}
				}
				ans = Math.min(ans, cnt);
			}
			System.out.println(ans);
		}
	}
}
class Pt implements Comparable<Pt> {
	int x, y;
	public int compareTo(Pt pt) {
		if (y == pt.y) return x - pt.x;
		return y - pt.y;
	}
	public String toString() {
		return "(X: " + x + ", Y: " + y + ")";
	}
}