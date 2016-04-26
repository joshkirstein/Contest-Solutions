import java.util.*;

public class D {
	static int X;
	static int rows, cols;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int caze = 1; caze <= T; caze++) {
			X = sc.nextInt();
			rows = sc.nextInt();
			cols = sc.nextInt();
			String ans = "GABRIEL";
			MAIN:
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (!dfs(new boolean[rows][cols], i, j, X-1)) {
						ans = "RICHARD";
						break MAIN;
					}
				}
			}
			System.out.printf("Case #%d: %s\n", caze, ans);
		}
	}
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
	static boolean dfs(boolean[][] grid, int x, int y, int left) {
		grid[x][y] = true;
		if (left == 0 && !canFill(grid)) {
			grid[x][y] = false;
			return false;
		}
		if (left == 0) {
			grid[x][y] = false;
			return true;
		}
		for (int k = 0; k < 4; k++) {
			int DX = x+dx[k];
			int DY = y+dy[k];
			if (DX >= 0 && DX < rows && DY >= 0 && DY < cols && !grid[DX][DY]) {
				if (!dfs(grid, DX, DY, left-1)) {
					grid[x][y] = false;
					return false;
				}
			}
		}
		grid[x][y] = false;
		return true;
	}

	static int dfs2(boolean[][] grid, int x, int y) {
		grid[x][y] = true;
		int cnt = 1;
		for (int k = 0; k < 4; k++) {
			int DX = x+dx[k];
			int DY = y+dy[k];
			if (DX >= 0 && DX < rows && DY >= 0 && DY < cols && !grid[DX][DY]) {
				cnt += dfs2(grid, DX, DY);
			}
		}
		return cnt;
	}

	static boolean canFill(boolean[][] cur) {
		//every region has to be divisible by X
		boolean[][] reg = new boolean[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				reg[i][j] = cur[i][j];
			}
		}
		for (boolean[] arr : cur) {
			System.out.println(Arrays.toString(arr));
		}
		System.out.println();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (reg[i][j]) continue;
				int cnt = dfs2(reg, i, j);
				if (cnt % X != 0) return false;
			}
		}
		return true;
	}
}