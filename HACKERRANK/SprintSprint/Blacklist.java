import java.util.*;

public class Blacklist {
	static int N, K;
	static int[][] merc;
	static Integer[][][] memo;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		merc = new int[K][N];
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < N; j++) {
				merc[i][j] = sc.nextInt();
			}
		}
		memo = new Integer[1<<K+1][10][20];
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < K; i++) {
			best = Math.min(best, dp((1 << i), i, 0));
		}
		System.out.println(best);
	}

	static int dp(int used_merc, int mer, int gangster) {
		if (gangster >= N) return 0;
		if (memo[used_merc][mer][gangster] != null) 
			return memo[used_merc][mer][gangster];
		int best = 10000000;
		int sum = 0;
		for (int i = gangster; i < N; i++) {
			sum += merc[mer][i];
			for (int j = 0; j < K; j++) {
				if ((used_merc & (1 << j)) == 0) {
					best = Math.min(best, sum+dp(used_merc | (1<<j), j, i+1));
				}
			}
		}
		best = Math.min(best, sum);
		return memo[used_merc][mer][gangster]=best;
	}
}