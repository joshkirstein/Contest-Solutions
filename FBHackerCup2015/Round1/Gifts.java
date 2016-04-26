import java.util.*;

public class Gifts {
	static int N;
	static ArrayList[] children;
	static LinkedList[] memo;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		System.setErr(System.out);
		for (int caze = 1; caze <= T; caze++) {
			N = sc.nextInt();
			children = new ArrayList[N];
			memo = new LinkedList[N];
			for (int i = 0; i < N; i++) {
				int parent = sc.nextInt()-1;
				if (parent >= 0) {
					if (children[parent] == null) children[parent] = new ArrayList<Integer>();
					children[parent].add(i);
				}
			}
			System.out.println("Case #" + caze + ": " + dp(0, 0));
		}
	}
	static LinkedList<Pair> mem;
	static int dp(int root, int prev) {
		mem = memo[root];
		if (mem != null) {
			for (Pair p : mem) {
				if (p.key == prev) return p.value;
			}
		} else {
			memo[root] = new LinkedList<Pair>();
		}
		int cost = Integer.MAX_VALUE;
		for (int i = 1; i <= 20; i++) {
			if (i == prev) continue;
			int sub_cost = i;
			if (children[root] != null)
			for (int child : (ArrayList<Integer>) children[root]) {
				sub_cost += dp(child, i);
			}
			cost = Math.min(cost, sub_cost);
		}
		mem = memo[root];
		mem.add(new Pair(prev, cost));
		return cost;
	}
}
class Pair {
	int key, value;
	public Pair(int key, int value) {
		this.key = key;
		this.value = value;
	}
}