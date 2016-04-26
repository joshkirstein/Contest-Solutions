import java.util.*;

public class Grid {
	static boolean vis[][];
	static int[][] grid;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int rows = sc.nextInt(), cols = sc.nextInt();
		grid = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = sc.nextInt();
			}
		}
		vis = new boolean[rows][cols];
		int maxCnt = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] == 1 && !vis[i][j]) {
					cnt = 0;
					dfs(i, j);
					maxCnt = Math.max(maxCnt, cnt);
				}
			}
		}
		System.out.println(maxCnt);
	}
	static int[] dx = {1, 0, -1, 0, 1, -1, -1, 1};
	static int[] dy = {0, 1, 0, -1, 1, -1, 1, -1};
	static int cnt = 0;
	static void dfs(int i, int j) {
		vis[i][j] = true;
		cnt++;
		for (int k = 0; k < dx.length; k++) {
			int di = i+dx[k];
			int dj = j+dy[k];
			if (di >= 0 && di < vis.length && dj >= 0 && dj < vis[0].length) {
				if (!vis[di][dj] && grid[di][dj] == 1) {
					dfs(di, dj);
				}
			}
		}
	}
}