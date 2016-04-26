import java.util.*;
import java.io.*;
public class WordLadder {
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String[] words = new String[N];
		for (int i = 0; i < N; i++) {
			words[i] = sc.next();
		}
		int[][] conn = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				String w1 = words[i];
				String w2 = words[j];
				int diff = 0;
				for (int k = 0; k < w1.length(); k++) {
					if (w1.charAt(k) != w2.charAt(k)) diff++;
				}
				if (diff <= 2) {
					conn[i][j] = diff;
					conn[j][i] = diff;
				}
			}
		}
		PriorityQueue<State> q = new PriorityQueue<State>();
		q.add(new State(0, 0, null, 0));
		Integer[][] minDist = new Integer[N][2];
		minDist[0][0] = 0;
		while (!q.isEmpty()) {
			State st = q.poll();
			if (st.wordIdx == 1) {
				if (st.usedExtra == 1) {
					System.out.println(st.word);
				} else {
					System.out.println(0);
				}
				System.out.println((st.steps));
				return;
			}
			for (int i = 0; i < N; i++) {
				if (i == st.wordIdx) continue;
				int dist = conn[st.wordIdx][i];
				if (dist == 1) {
					if (minDist[i][st.usedExtra] == null || minDist[i][st.usedExtra] > (st.steps+1)) {
						minDist[i][st.usedExtra] = st.steps+1;
						q.add(new State(i, st.usedExtra, st.word, st.steps+1));
					}
				} else if (dist == 2) {
					if (st.usedExtra == 1) continue;
					if (minDist[i][1] == null || minDist[i][1] > (st.steps+2)) {
						minDist[i][1] = st.steps+2;
						String word = "";
						boolean used = false;
						int minIdx = -1;

						char ci = Character.MAX_VALUE;
						char cj = Character.MAX_VALUE;
						for (int j = 0; j < words[i].length(); j++) {
							char c = words[i].charAt(j);
							char c2 = words[st.wordIdx].charAt(j);
							if (c != c2) {
								if (minIdx == -1) {
									minIdx = j;
									ci = c;
									cj = c2;
								} else {
									if (ci < cj) continue;
									if (ci > cj) {									
										minIdx = j;
										ci = c;
										cj = c2;
									}
								}
							}
						}
						for (int j = 0; j < words[i].length(); j++) {
							char c = words[i].charAt(j);
							char c2 = words[st.wordIdx].charAt(j);
							if (j == minIdx) word += c;
							else word += c2;
						}
						q.add(new State(i, 1, word, st.steps+2));
					}
				}
			}
		}
		System.out.println(0);
		System.out.println(-1);
	}
}
class State implements Comparable<State> {
	public int compareTo(State st) {
		if (steps == st.steps) {
			if (word == null) return -1;
			if (st.word == null) return 1;
			return word.compareTo(st.word);
		}
		return steps - st.steps;
	}
	int wordIdx;
	int usedExtra;
	public State(int wordIdx, int usedExtra, String word, int steps) {
		this.wordIdx = wordIdx;
		this.usedExtra = usedExtra;
		this.word = word;
		this.steps = steps;
	}
	public int steps;
	public String word;
}