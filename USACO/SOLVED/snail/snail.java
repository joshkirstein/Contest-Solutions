/*
ID: joshkir1
LANG: JAVA
TASK: snail
*/
import java.io.*;
import java.util.*;

class snail
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("snail.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snail.out")));
		N = sc.nextInt();
		int B = sc.nextInt();
		int[][] grid = new int[N][N];

		for (int i = 0; i < B; i++) {
			String pp = sc.next();
			int col = pp.charAt(0)-'A';
			int row = Integer.parseInt(pp.substring(1))-1;
			grid[row][col] = 2;
		}
		grid[0][0] = 1;
		dfs(0,0,grid,1);
		//for (int[] gg : grid) System.out.println(Arrays.toString(gg));
		System.out.println(best);
		out.println(best);
		out.close();
		System.exit(0);
	}
	static boolean isValid(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
	static int N;
	static int[] dx = {0,1,-1,0};
	static int[] dy = {-1,0,0,1};
	static int best = 0;
	static void dfs(int x, int y, int[][] grid, int cnt) {
		best = Math.max(best, cnt);
		//System.out.println("X: " + x + " Y: "+ y);
		for (int i = 0; i < 4; i++) {
			int tempx = x;
			int tempy = y;
			int temp = 0;
			while (isValid(tempx+dx[i], tempy+dy[i]) && grid[tempx+dx[i]][tempy+dy[i]] == 0) {
				tempx += dx[i];
				tempy += dy[i];
				grid[tempx][tempy] = 1;
				temp++;
			}
			//if (x == 2 && y == 4) System.out.println("FFF TEMPX: " + tempx + " TEMPY: " + tempy + " OTHER: " + grid[tempx+dx[i]][tempy+dy[i]]);
			if (temp != 0) {
				//if (x == 0 && y == 0) System.out.println("TEMPX: " + tempx + " TEMPY: " + tempy);
				if (isValid(tempx+dx[i], tempy+dy[i])) {
					if (grid[tempx+dx[i]][tempy+dy[i]] == 1) {
						best = Math.max(best, cnt+temp);
					} else {
						dfs(tempx, tempy, grid, cnt+temp);
					}
				} else {
					dfs(tempx, tempy, grid, cnt+temp);
				}
				while (tempx != x || tempy != y) {
					grid[tempx][tempy] = 0;
					tempx -= dx[i];
					tempy -= dy[i];
				}
			}
		}
	}
}