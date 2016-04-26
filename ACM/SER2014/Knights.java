import java.util.*;

// bitmask + counting + matrix multiplication
class Knights {
	
	static long MOD = 1000000009;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		rows = sc.nextInt();
		cols = sc.nextLong();
		if (cols == 1) {
			long val = 1;
			for (int i = 0; i < rows; i++) {
				val *= 2;
				val %= MOD;
			}
			System.out.println(val % MOD);
			return;
		}
		MAX = (1 << (2*rows));
		works = new boolean[MAX][MAX];
		invalid = new boolean[MAX];
		precompute();			
		seed = new long[MAX][MAX];
		for (int i = 0; i < MAX; i++) {
			for (int j = 0; j < MAX; j++) {
				if (works[i][j] && !invalid[i] && !invalid[j]) seed[i][j] = 1;
			}
		}

		long[][] ans = multiply(((int) cols/2)+1);
		int to = 0;
		if (cols % 2 != 0) {
			to = (1 << rows)-1;
		}
		long sum = 0;
		for (int i = 0; i <= to; i++) {
			sum += ans[0][i]%MOD;
			sum %= MOD;
		}
		System.out.println(sum%MOD);
	}

	static long[][] seed;

	static long[][] multiply(int power) {
		if (power == 0) {
			long[][] test = new long[MAX][MAX];
			test[0][0] = 1;
			return test;
		} else if (power == 1) {
			return seed;
		} else {
			if (power % 2 == 0) {
				long[][] ans = multiply(power/2);
				long[][] next = new long[MAX][MAX];
				//to i from j
				for (int i = 0; i < MAX; i++) {
					if (invalid[i]) continue;
					for (int j = 0; j < MAX; j++) {
						if (invalid[j]) continue;
						for (int k = 0; k < MAX; k++) {
							if (invalid[k]) continue;
							next[i][j] += (ans[i][k]%MOD)*(ans[k][j]%MOD);
							next[i][j] %= MOD;
						}
					}
				}
				return next;
			} else {
				long[][] ans = multiply(power-1);
				long[][] next = new long[MAX][MAX];
				//to i from j
				for (int i = 0; i < MAX; i++) {
					if (invalid[i]) continue;
					for (int j = 0; j < MAX; j++) {
						if (invalid[j]) continue;
						for (int k = 0; k < MAX; k++) {
							if (invalid[k]) continue;
							next[i][j] += (ans[i][k]%MOD)*(seed[k][j]%MOD);
							next[i][j] %= MOD;
						}
					}
				}
				return next;
			}
		}
	}

	static int rows;
	static long cols;
	static int MAX;
	static boolean[][] works;
	static boolean[] invalid;

	static int[] dx = new int[] { -2, -1, 1, 2, -1, 1, -2, 2 };
	static int[] dy = new int[] { -1, -2, 2, 1, 2, -2, 1, -1 };

	static void precompute() {
		boolean[][][] sup = new boolean[MAX][rows][4];
		for (int i = 0; i < MAX; i++) {
			boolean[][] cur = new boolean[rows][4];
			for (int j = 0; j < rows; j++) {
				if ((i & (1 << j)) != 0) {
					cur[j][0] = true;
				}
			}
			for (int j = rows; j < 2*rows; j++) {
				if ((i & (1 << j)) != 0) {
					cur[j-rows][1] = true;
				}
			}
			sup[i] = cur;
		}
		MAIN1:
		for (int i = 0; i < MAX; i++) {
			for (int k = 0; k < rows; k++) {
				for (int l = 0; l < 2; l++) {
					if (!sup[i][k][l]) continue;
					for (int m = 0; m < dx.length; m++) {
						int DK = k+dx[m];
						int DL = l+dy[m];
						if (DK >= 0 && DL >= 0 && DK < rows && DL < 2) {
							if (sup[i][DK][DL]) {
								invalid[i] = true;
								continue MAIN1;
							}
						}
					}
				}
			}
			for (int j = 0; j < MAX; j++) {
				boolean good = true;
				MAIN:
				for (int k = 0; k < rows; k++) {
					for (int l = 0; l < 2; l++) {
						if (!sup[i][k][l]) continue;
						for (int m = 0; m < dx.length; m++) {
							int DK = k+dx[m];
							int DL = l+dy[m];
							if (DK >= 0 && DL >= 0 && DK < rows && DL < 4) {
								if (DL >= 2) {
									if (sup[j][DK][DL-2]) { good = false; break MAIN; }
								}
							}
						}
					}
				}

				works[i][j] = good;
			}
		}
	}
}