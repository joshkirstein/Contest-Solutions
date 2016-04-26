import java.util.*;

public class A{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
		boolean[][] grid = new boolean[n][m];
		for (int i = 0; i < k; i++) {
			int x = sc.nextInt()-1, y = sc.nextInt()-1;
			grid[x][y] = true;
			boolean won = false;
			try {
				if (grid[x][y] && grid[x-1][y] && grid[x-1][y-1] && grid[x][y-1]) {
					won = true;
				}
			} catch (Exception ex) { }
			try {
				if (grid[x][y] && grid[x-1][y] && grid[x-1][y+1] && grid[x][y+1]) {
					won = true;
				}
			} catch (Exception ex) { }
			try {
				if (grid[x][y] && grid[x+1][y] && grid[x+1][y+1] && grid[x][y+1]) {
					won = true;
				}
			} catch (Exception ex) { }
			try {
				if (grid[x][y] && grid[x+1][y] && grid[x+1][y-1] && grid[x][y-1]) {
					won = true;
				}
			} catch (Exception ex) { }
			if (won) {
				System.out.println(i+1);
				return;
			}
		}
		System.out.println("0");
	}
}
