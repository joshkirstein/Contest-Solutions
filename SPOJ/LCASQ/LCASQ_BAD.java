import java.util.*;

public class LCASQ_BAD {
	
	static HashMap<Integer, ArrayList<Integer>> tree = new HashMap<Integer, ArrayList<Integer>>();
	static HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> child = new ArrayList<Integer>();
			tree.put(i, child);
			int amt = sc.nextInt();
			for (int j = 0; j < amt; j++) {
				int sup = sc.nextInt();
				if (parent.containsKey(sup)) throw new Exception();
				parent.put(sup, i);
				child.add(sup);
			}
		}
		int Q = sc.nextInt();
		for (int i = 0; i < Q; i++) {
			int u = sc.nextInt(), v = sc.nextInt();
			query(u, v);
		}
	}

	public static void query(int u, int v) {
		int val = -1;
		HashSet<Integer> visited = new HashSet<Integer>();
		int cur = u;
		while (true) {
			visited.add(cur);
			if (cur == 0) break;
			cur = parent.get(cur);
		}
		cur = v;
		while (true) {
			if (visited.contains(cur)) {
				val = cur;
				break;
			} else {
				cur = parent.get(cur);
			}
		}
		System.out.println(val);
	}
}