/*
ID: joshkir1
LANG: JAVA
TASK: shuttle
*/
import java.util.*;
import java.io.*;

public class shuttle {

	static int N;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new FileReader("./shuttle.in"));
		PrintWriter pw = new PrintWriter(new FileWriter("./shuttle.out"));
		N = sc.nextInt();

		HashSet<State> vis = new HashSet<State>();
		int[] startW = new int[N];
		int[] endW = new int[N];
		for (int i = 0; i < N; i++) startW[i] = i;
		for (int i = 0; i < N; i++) endW[i] = N+i+1;
		State start = new State(startW, N);
		State end = new State(endW, N);
		start.move = 0;
		start.dist = 0;
		start.pred = null;
		vis.add(start);
		PriorityQueue<State> pq = new PriorityQueue<State>(1, new Comparator<State>() {
			public int compare(State s1, State s2) {
				int dist1 = s1.dist;
				int dist2 = s2.dist;
				if (dist1 == dist2) {
					int move1 = s1.move;
					int move2 = s2.move;
					return move2-move1;
				} else {
					return dist1-dist2;
				}
			}
		});
		pq.add(start);
		while (!pq.isEmpty()) {
			State poll = pq.poll();
			int theDist = poll.dist;
			if (poll.equals(end)) {
				ArrayList<Integer> output = new ArrayList<Integer>();

				State cur = poll;
				while (true) {
					State prev = cur.pred;
					if (prev == null) break;
					int mov = cur.move+1;
					output.add(0, mov);
					cur = prev;
				}

				//PRINT
				StringBuilder br = new StringBuilder("");
				int i = 0;
				MAIN:
				while (i < output.size()) {
					int last = i + 20;
					boolean star = false;
					boolean oops = false;
					for (int j = i; j < last; j++) {
						if (j >= output.size()) {
							oops = true;
							break;
						}
						if (!star) {
							br.append(output.get(j));
							star = true;
						} else {
							br.append(" " + output.get(j));
						}
					}
					br.append("\n");
					if (oops) break;
					i = last;
				}
				pw.print(br.toString());
				pw.close();
				System.exit(0);
				break;
			} else {
				int[] board = new int[2*N+1];
				board[poll.spacePos] = 2;
				int[] wpos = poll.wpos;
				for (int i = 0; i < wpos.length; i++) {
					board[wpos[i]] = 1;
				}
				//0 is black 1 is white 2 is space

				int lv = poll.spacePos-1;
				if (lv >= 0 && board[lv] == 1) {

					//put lv into space
					board[poll.spacePos] = board[lv];
					board[lv] = 2;
					int[] wp = new int[N];
					int newSpace = lv;
					int idx = 0;
					for (int i = 0; i < 2*N+1; i++) {
						if (board[i] == 1) {
							wp[idx++] = i;
						}
					}
					State nextState = new State(wp, newSpace);
					if (!vis.contains(nextState)) {
						nextState.pred = poll;
						nextState.move = lv;
						nextState.dist = theDist+1;
						vis.add(nextState);
						pq.add(nextState);
					} 
					//reverse
					board[lv] = board[poll.spacePos];
					board[poll.spacePos] = 2;
				}
				lv = poll.spacePos+1;

				if (lv < 2*N+1 && board[lv] == 0) {
					//put lv into space
					board[poll.spacePos] = board[lv];
					board[lv] = 2;

					int[] wp = new int[N];
					int newSpace = lv;
					int idx = 0;
					for (int i = 0; i < 2*N+1; i++) {
						if (board[i] == 1) {
							wp[idx++] = i;
						}
					}
					State nextState = new State(wp, newSpace);
					if (!vis.contains(nextState)) {
						nextState.pred = poll;
						nextState.move = lv;
						nextState.dist = theDist+1;
						vis.add(nextState);
						pq.add(nextState);
					}

					//reverse
					board[lv] = board[poll.spacePos];
					board[poll.spacePos] = 2;
				}

				lv = poll.spacePos+2;


				if (lv < 2*N+1 && board[lv-1] != board[lv] && board[lv] == 0) {
					//put lv into space
					board[poll.spacePos] = board[lv];
					board[lv] = 2;

					int[] wp = new int[N];
					int newSpace = lv;
					int idx = 0;
					for (int i = 0; i < 2*N+1; i++) {
						if (board[i] == 1) {
							wp[idx++] = i;
						}
					}
					State nextState = new State(wp, newSpace);
					if (!vis.contains(nextState)) {
						nextState.pred = poll;
						nextState.move = lv;
						nextState.dist = theDist+1;
						vis.add(nextState);
						pq.add(nextState);
					}

					//reverse
					board[lv] = board[poll.spacePos];
					board[poll.spacePos] = 2;
				}

				lv = poll.spacePos-2;

				if (lv >= 0 && board[lv+1] != board[lv] && board[lv] == 1) {
					//put lv into space
					board[poll.spacePos] = board[lv];
					board[lv] = 2;

					int[] wp = new int[N];
					int newSpace = lv;
					int idx = 0;
					for (int i = 0; i < 2*N+1; i++) {
						if (board[i] == 1) {
							wp[idx++] = i;
						}
					}
					State nextState = new State(wp, newSpace);
					if (!vis.contains(nextState)) {
						nextState.pred = poll;
						nextState.move = lv;
						nextState.dist = theDist+1;
						vis.add(nextState);
						pq.add(nextState);
					}

					//reverse
					board[lv] = board[poll.spacePos];
					board[poll.spacePos] = 2;
				}
			}
		}
	}
}
class State {
	State pred;
	int move;
	int dist;
	int[] wpos;
	int spacePos;
	int hashCode;
	public State(int[] wpos, int spacePos) {
		Arrays.sort(wpos);
		this.wpos = wpos;
		this.spacePos = spacePos;
		this.hashCode = Arrays.hashCode(wpos) + spacePos ^ 72;
	}

	public int hashCode() {
		return hashCode;
	}

	public boolean equals(Object o) {
		State s = (State) o;
		if (s.spacePos == spacePos) {
			return Arrays.equals(wpos, s.wpos);
		}
		return false;
	}

}