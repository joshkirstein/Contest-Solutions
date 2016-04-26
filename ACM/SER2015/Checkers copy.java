import java.util.*;
public class Checkers {
	static int N;
	static char[][] grid;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		grid = new char[N][N];
		ArrayList<Pos> whites = new ArrayList<Pos>();
		ArrayList<Pos> blacks = new ArrayList<Pos>();
		ArrayList<Pos> free = new ArrayList<Pos>();
		for (int i = 0; i < N; i++) {
			String word = sc.next();
			for (int j = 0; j < N; j++) {
				grid[i][j] = word.charAt(j);
				if (grid[i][j] == 'W') whites.add(new Pos(i,j));
				if (grid[i][j] == 'B') blacks.add(new Pos(i,j));
				if (grid[i][j] == '_') free.add(new Pos(i,j));
			}
		}
		int cnt = 0;
		for (Pos blck : blacks) {
			grid[blck.x][blck.y] = '_';
			int[][] deg = get_degree_graph();
			if (deg[blck.x][blck.y] == 0) continue;
			if (deg[blck.x][blck.y]%2==0) {
				boolean[][] vis = new boolean[N][N];
				Queue<Pos> qpos = new LinkedList<Pos>();
				vis[blck.x][blck.y] = true;
				qpos.add(new Pos(blck.x,blck.y));
				while (!qpos.isEmpty()) {
					Pos poll = qpos.poll();
					for (int k = 0; k < 4; k++) {
						int di = poll.x+jumpdx[k];
						int dj = poll.y+jumpdy[k];
						int midi = poll.x+jumpdx[k]/2;
						int midj = poll.y+jumpdy[k]/2;
						if (di >= 0 && di < N && dj >= 0 && dj < N
							&& grid[di][dj] == '_' && grid[midi][midj] == 'W'
							&& deg[di][dj] > 0 && deg[di][dj] % 2 == 0) {
							vis[midi][midj] = true;
							if (!vis[di][dj]) {
								vis[di][dj] = true;
								qpos.add(new Pos(di,dj));
							}
						}
					}
				}
				boolean works = true;
				for (Pos w : whites) {
					if (!vis[w.x][w.y]) {
						works = false;
						break;
					}
				}
				if (works) cnt++;
			} else {
				for (Pos end : free) {
					if (deg[end.x][end.y] == 0) continue;
					if (deg[end.x][end.y] % 2 == 0) continue;
					boolean[][] vis = new boolean[N][N];
					Queue<Pos> qpos = new LinkedList<Pos>();
					vis[blck.x][blck.y] = true;
					qpos.add(new Pos(blck.x,blck.y));
					while (!qpos.isEmpty()) {
						Pos poll = qpos.poll();
						for (int k = 0; k < 4; k++) {
							int di = poll.x+jumpdx[k];
							int dj = poll.y+jumpdy[k];
							int midi = poll.x+jumpdx[k]/2;
							int midj = poll.y+jumpdy[k]/2;
							if (di >= 0 && di < N && dj >= 0 && dj < N
								&& grid[di][dj] == '_' && grid[midi][midj] == 'W') {
								boolean canMove = false;
								canMove |= deg[di][dj] > 0 && deg[di][dj] % 2 == 0;
								canMove |= di == end.x && dj == end.y;
								canMove |= di == blck.x && dj == blck.y;
								if (canMove) {
									vis[midi][midj] = true;
									if (!vis[di][dj]) {
										vis[di][dj] = true;
										qpos.add(new Pos(di,dj));
									}
								}
							}
						}
					}
					boolean works = true;
					for (Pos w : whites) {
						if (!vis[w.x][w.y]) {
							works = false;
							break;
						}
					}
					if (works) {
						cnt++;
						break;
					}
				}
			}
			grid[blck.x][blck.y] = 'B';
		}
		System.out.println(cnt);
	}

	static int[] jumpdx = {-2,-2,2,2};
	static int[] jumpdy = {-2,2,-2,2};

	static int[][] get_degree_graph() {
		int[][] degs = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == '_') {
					//only for free spots
					for (int k = 0; k < 4; k++) {
						int di = i+jumpdx[k];
						int dj = j+jumpdy[k];
						int midi = i+jumpdx[k]/2;
						int midj = j+jumpdy[k]/2;
						if (di >= 0 && di < N && dj >= 0 && dj < N
							&& grid[di][dj] == '_' && grid[midi][midj] == 'W') {
							degs[i][j]++;
						}
					}
				}
			}
		}
		return degs;
	}
}
class Pos {
	int x, y;
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}