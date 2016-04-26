import java.util.*;

public class Grid {
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[][] grid = new int[N][M];
		int[][] min = new int[N][M];
		sc.nextLine();
		for (int i = 0; i < N; i++) {
			String s = sc.nextLine();
			for (int j = 0; j < M; j++) {
				grid[i][j] = (int) (s.charAt(j)-'0');
				min[i][j] = -1;
			}
		}
		min[0][0] = 0;
		Queue<Pair> q = new LinkedList<Pair>();
		q.add(new Pair(0,0));
		while (!q.isEmpty()) {
			Pair poll = q.poll();
			int dist = min[poll.x][poll.y];
			int dig = grid[poll.x][poll.y];
			if (poll.x-dig>=0 && min[poll.x-dig][poll.y] == -1) {
				min[poll.x-dig][poll.y] = dist+1;
				q.add(new Pair(poll.x-dig, poll.y));
			}
			if (poll.x+dig < N && min[poll.x+dig][poll.y] == -1) {
				min[poll.x+dig][poll.y] = dist+1;
				q.add(new Pair(poll.x+dig, poll.y));
			}
			if (poll.y-dig>=0  && min[poll.x][poll.y-dig] == -1) {
				min[poll.x][poll.y-dig] = dist+1;
				q.add(new Pair(poll.x, poll.y-dig));
			}
			if (poll.y+dig<M  && min[poll.x][poll.y+dig] == -1) {
				min[poll.x][poll.y+dig] = dist+1;
				q.add(new Pair(poll.x, poll.y+dig));
			}
		}
		System.out.println(min[N-1][M-1]);
	}
}
class Pair {
	int x, y;
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}