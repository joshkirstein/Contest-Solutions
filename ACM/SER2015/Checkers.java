import java.util.*;

public class Checkers {
	
	static int[] dx = {2, -2, -2, 2};
	static int[] dy = {2, 2, -2, -2};
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		sc.nextLine();
		char[][] grid = new char[N][N];
		ArrayList<Pos> blacks = new ArrayList<Pos>();
		for (int i = 0; i < N; i++) {
			String word = sc.nextLine();
			for (int j = 0; j < N; j++) {
				grid[i][j] = word.charAt(j);
				if (grid[i][j] == 'B') blacks.add(new Pos(i,j));
			}
		}
		int[][] degrees = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == '_' || grid[i][j] == 'B') {
					for (int k = 0; k < 4; k++) {
						int DI = i+dx[k];
						int DJ = j+dy[k];
						int midDI = i+dx[k]/2;
						int midDJ = j+dy[k]/2;
						if (DI >= 0 && DI < N && DJ >= 0 && DJ < N) {
							if (grid[midDI][midDJ] == 'W' && grid[DI][DJ] != 'B') {
								degrees[i][j]++;
							}
						}
					}
				}
			}
		}
		int cnt = 0;
		for (Pos black : blacks) {
			grid[black.i][black.j] = '_';
			//add in extra degree from this black jumping...
			for (int k = 0; k < 4; k++) {
				int DI = black.i+dx[k];
				int DJ = black.j+dy[k];
				int midDI = black.i+dx[k]/2;
				int midDJ = black.j+dy[k]/2;
				if (DI >= 0 && DI < N && DJ >= 0 && DJ < N) {
					if (grid[midDI][midDJ] == 'W' && grid[DI][DJ] != 'B') {
						degrees[DI][DJ]++;
					}
				}
			}

			int[][] degree_copy = new int[N][N];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (grid[i][j] == 'B' && !(i == black.i && j == black.j)) {

					} else {
						degree_copy[i][j] = degrees[i][j];
					}
				}
			}

			if (degree_copy[black.i][black.j] % 2 == 0) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (degree_copy[i][j] % 2 != 0) degree_copy[i][j] = 0;
					}
				}
				if (works(black.i,black.j,degree_copy,grid)) cnt++;
			} else {
				boolean cando = false;
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (degree_copy[i][j] % 2 != 0 && !(black.i != i && black.j == j))	 {
							//bi,bj->i,j
							int[][] new_degree_copy = new int[N][N];
							for (int ii = 0; ii < N; ii++) {
								for (int jj = 0; jj < N; jj++) {
									if (degree_copy[ii][jj] % 2 == 0 || (ii == i && jj == j) || (ii == black.i && jj == black.j)) {
										new_degree_copy[ii][jj] = degree_copy[ii][jj];
									}
								}
							}
							cando |= works(black.i,black.j, new_degree_copy,grid);
						}
					}
				}
				if (cando) cnt++;
			}

			//remove the extra degree
			for (int k = 0; k < 4; k++) {
				int DI = black.i+dx[k];
				int DJ = black.j+dy[k];
				int midDI = black.i+dx[k]/2;
				int midDJ = black.j+dy[k]/2;
				if (DI >= 0 && DI < N && DJ >= 0 && DJ < N) {
					if (grid[midDI][midDJ] == 'W' && grid[DI][DJ] != 'B') {
						degrees[DI][DJ]--;
					}
				}
			}

			grid[black.i][black.j] = 'B';
		}
		System.out.println(cnt);
	}

	static boolean works(int bi, int bj, int[][] deg, char[][] grid) {
		int N = deg.length;
		boolean[][] vis = new boolean[deg.length][deg.length];
		Queue<Pos> q = new LinkedList<Pos>();
		vis[bi][bj] = true;
		q.add(new Pos(bi, bj));
		while (!q.isEmpty()) {
			Pos pop = q.poll();
			for (int k = 0; k < 4; k++) {
				int DI = pop.i+dx[k];
				int DJ = pop.j+dy[k];	
				int midDI = pop.i+dx[k]/2;
				int midDJ = pop.j+dy[k]/2;			
				if (DI >= 0 && DI < N && DJ >= 0 && DJ < N) {
					if (deg[DI][DJ] > 0) {
						vis[midDI][midDJ] = true;
						if (!vis[DI][DJ]) {
							vis[DI][DJ] = true;
							q.add(new Pos(DI, DJ));
						}
					}
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == 'W' && !vis[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
class Pos {
	int i, j;
	public Pos(int i, int j) {
		this.i = i;
		this.j = j;
	}
}