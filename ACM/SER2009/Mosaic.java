import java.util.*;

// bitmask dp + counting
class Mosaic {
	
	static int MOD = 1_000_000;
	static int rows, cols;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			rows = sc.nextInt();
			cols = sc.nextInt();
			if (rows == 0 && cols == 0) break;
			MAX = (1<<rows);
			precompute();

			int[] ans = new int[MAX];
			ans[0] = 1;
			for (int k = 0; k < cols/2; k++) {
				//multiply
				int[] trans = new int[MAX];

				//going from i
				for (int i = 0; i < MAX; i++) {
					//to j
					for (Object o : jump[rows][i]) {
						int j = (Integer) o;
						trans[j] += ((ans[i]%MOD)*(numways[rows][i][j]%MOD))%MOD;
					}
				}
				
				ans = trans;
			}
			System.out.println(ans[cols % 2 == 0 ? 0 : MAX-1]%MOD);
		}
	}

	static int[][][] numways = new int[11][][];
	static HashSet[][] jump = new LinkedHashSet[11][];
	static int MAX;
	static void precompute() {
		if (numways[rows] != null) return;
		numways[rows] = new int[MAX][MAX];
		jump[rows] = new LinkedHashSet[MAX];
		//numways[i][j] = num ways from a starting col of i
		//to an ending column of j
		for (int i = 0; i < MAX; i++) {
			jump[rows][i] = new LinkedHashSet();
			boolean[][] rep = new boolean[rows][3];
			for (int j = 0; j < rows; j++) {
				if ((i & (1 << j)) != 0) {
					rep[j][0] = true;
				}
			}
			flood(rep, numways, i);
		}
	}

	static boolean isValid(int i, int j) {
		return i >= 0 && j >= 0 && i < rows && j < 3;
	}

	static void flood(boolean[][] cur, int[][][] numways, int fromMask) {
		boolean good = true;
		int toMask = 0;
		for (int i = 0; i < rows; i++) {
			if (!cur[i][1] || !cur[i][0]) {
				good = false;
			}
			if (cur[i][2]) {
				toMask |= (1 << i);
			}
		}
		if (good) {
			numways[rows][fromMask][toMask]++;
			jump[rows][fromMask].add(toMask);
			return;
		}
		//try and place first piece possible:
		for (int j = 0; j <= 1; j++) {
			for (int i = 0; i < rows; i++) {
				if (!cur[i][j]) {
					boolean didPlace = false;

					if (isValid(i+1, j) && isValid(i, j+1) && isValid(i+1, j+1)) {
						if (!cur[i+1][j] && !cur[i][j+1] && !cur[i+1][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i+1][j] = true;
							cur[i][j+1] = true;
							cur[i+1][j+1] = true;
							flood(cur, numways, fromMask);	
							cur[i][j] = false;
							cur[i+1][j] = false;
							cur[i][j+1] = false;
							cur[i+1][j+1] = false;
						}
					}

					if (isValid(i, j+1) && isValid(i+1, j+1)) {
						if (!cur[i][j+1] && !cur[i+1][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i][j+1] = true;
							cur[i+1][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i][j+1] = false;
							cur[i+1][j+1] = false;
						}
					}

					if (isValid(i+1, j) && isValid(i, j+1)) {
						if (!cur[i+1][j] && !cur[i][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i+1][j] = true;
							cur[i][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i+1][j] = false;
							cur[i][j+1] = false;
						}
					}

					if (isValid(i+1, j) && isValid(i+1, j+1)) {
						if (!cur[i+1][j] && !cur[i+1][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i+1][j] = true;
							cur[i+1][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i+1][j] = false;
							cur[i+1][j+1] = false;
						}
					}

					if (isValid(i, j+1) && isValid(i-1, j+1)) {
						if (!cur[i][j+1] && !cur[i-1][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i][j+1] = true;
							cur[i-1][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i][j+1] = false;
							cur[i-1][j+1] = false;
						}
					}

					if (isValid(i-1, j) && isValid(i, j+1)) {
						if (!cur[i-1][j] && !cur[i][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i-1][j] = true;
							cur[i][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i-1][j] = false;
							cur[i][j+1] = false;
						}
					}

					if (isValid(i-1, j) && isValid(i-1, j+1)) {
						if (!cur[i-1][j] && !cur[i-1][j+1]) {
							didPlace = true;
							cur[i][j] = true;
							cur[i-1][j] = true;
							cur[i-1][j+1] = true;
							flood(cur, numways, fromMask);
							cur[i][j] = false;
							cur[i-1][j] = false;
							cur[i-1][j+1] = false;
						}
					}
					return;
				}
			}
		}
	}
}