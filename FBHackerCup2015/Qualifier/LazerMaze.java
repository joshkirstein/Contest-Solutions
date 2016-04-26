import java.util.*;

public class LazerMaze {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int caze = 0;
		while (T-- > 0) {
			caze++;

			int M = sc.nextInt(), N = sc.nextInt();
			char[][] grid = new char[M][N];
			ArrayList<Point> lazers = new ArrayList<Point>();
			int sx = -1, sy = -1;
			int gx = -1, gy = -1;
			for (int i = 0; i < M; i++) {
				String s = sc.next();
				for (int j = 0; j < N; j++) {
					grid[i][j] = s.charAt(j);
					if (grid[i][j] == '^' || grid[i][j] == 'v' || grid[i][j] == '<' || grid[i][j] == '>') {
						lazers.add(new Point(i, j));
					}
					if (grid[i][j] == 'S') {
						sx = i;
						sy = j;
					}
					if (grid[i][j] == 'G') {
						gx = i;
						gy = j;
					}
				}
			}
			Queue<State> q = new LinkedList<State>();
			q.add(new State(sx, sy, 0));
			boolean[][][] vis = new boolean[M][N][4];
			vis[sx][sy][0] = true;
			boolean works = false;
			int ans = -1;
			while (!q.isEmpty()) {
				State cur = q.poll();

				if (cur.x == gx && cur.y == gy) {
					works = true;
					ans = cur.step;
					break;
				}


				for (int i = 0; i < dx.length; i++) {
					int DX = cur.x + dx[i];
					int DY = cur.y + dy[i];
					if (DX >= 0 && DX < M && DY >= 0 && DY < N) {
						if (grid[DX][DY] == '.' || grid[DX][DY] == 'G' || grid[DX][DY] == 'S') {
							//valid
							//check if alive
							boolean valid = true;
							MAIN:
							for (Point p : lazers) {
								//int getDir1 = dir(p, cur.step, grid);
								int getDir2 = dir(p, cur.step+1, grid);
								int x = p.x;
								int y = p.y;
								/*x += dx[getDir1];
								y += dy[getDir1];
								while (valid(x, y, M, N) && (grid[x][y] == '.' || grid[x][y] == 'S' || grid[x][y] == 'G')) {
									if (x == DX && y == DY) {
										valid = false;
										break MAIN;
									}
									x += dx[getDir1];
									y += dy[getDir1];
								}*/
								x = p.x; y = p.y;
								x += dx[getDir2];
								y += dy[getDir2];
								while (valid(x, y, M, N) && (grid[x][y] == '.' || grid[x][y] == 'S' || grid[x][y] == 'G')) {
									if (x == DX && y == DY) {
										valid = false;
										break MAIN;
									}
									x += dx[getDir2];
									y += dy[getDir2];
								}
							}
							if (valid) {
								if (!vis[DX][DY][(cur.step+1)%4]) {
									vis[DX][DY][(cur.step+1)%4] = true;
									q.add(new State(DX, DY, cur.step+1));
								}
							}
						}
					}
				}
			}

			System.out.println("Case #" + (caze) + ": " + (ans == -1 ? "impossible" : ans));
			
		}
	}

	static boolean valid(int x, int y, int M, int N) {
		return x >= 0 && x < M && y >= 0 && y < N;
	}

	static int dir(Point p, int step, char[][] grid) {
		int move = step % 4;
		int curDir = 0;
		if (grid[p.x][p.y] == 'v') curDir = 0;
		if (grid[p.x][p.y] == '^') curDir = 2;
		if (grid[p.x][p.y] == '<') curDir = 1;
		if (grid[p.x][p.y] == '>') curDir = 3;
		curDir += move;
		curDir %= 4;
		return curDir;
	}

	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
}
class State {
	int x, y, step;
	public State(int x, int y, int step) {
		this.x = x;
		this.y = y;
		this.step = step;
	}
}
class Point {
	int x, y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}