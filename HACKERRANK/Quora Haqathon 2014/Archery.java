import java.util.*;

public class Archery {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		boolean[] isRad = new boolean[1000001];
		for (int i = 0; i < N; i++) {
			isRad[sc.nextInt()] = true;
		}
		int[] sum = new int[1000001];
		for (int i = 0; i < 1000001; i++) {
			if (isRad[i]) {
				sum[i] += 1;
			}
			if (i != 0) {
				sum[i] += sum[i-1];
			}
		}
		int M = sc.nextInt();
		int cnt = 0;
		for (int i = 0; i < M; i++) {
			int x1 = sc.nextInt(), y1 = sc.nextInt();
			int x2 = sc.nextInt(), y2 = sc.nextInt();
			double d1 = dist(x1, y1);
			double d2 = dist(x2, y2);
			if (d2 < d1) {
				int tempx = x1;
				int tempy = y1;
				x1 = x2;
				y1 = y2;
				x2 = tempx;
				y2 = tempy;
			}
			int r1 = radius1(x1, y1);
			int r2 = radius2(x2, y2);
			if (r2 < r1) continue;
			cnt += sum[Math.min(r2, 1000000)]-sum[Math.min(1000000, Math.max(r1-1, 0))];
		}
		System.out.println(cnt);
	}

	static double dist(int x1, int y1) {
		return Math.hypot(x1, y1);
	}

	static int radius1(int x1, int y1) {
		return (int) Math.ceil(dist(x1, y1));
	}


	static int radius2(int x1, int y1) {
		return (int) Math.floor(dist(x1, y1));
	}
}