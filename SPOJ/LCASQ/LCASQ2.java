import java.util.*;

public class LCASQ2 {
	
	static HashMap<Integer, ArrayList<Integer>> tree;
	static HashMap<Integer, Integer> parents;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		tree = new HashMap<Integer, ArrayList<Integer>>();
		parents = new HashMap<Integer, Integer>();
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			tree.put(i, list);
			int amt = sc.nextInt();
			for (int j = 0; j < amt; j++) {
				int val = sc.nextInt();
				list.add(val);
				parents.put(val, i);
			}
		}
		int root = 0;
		LCA lca = new LCA(tree, root, N, parents);
		int Q = sc.nextInt();
		for (int i = 0; i < Q; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			if (v < u) {
				int temp = v;
				v = u;
				u = temp;
			}
			int val = lca.query(u, v);
			System.out.print(val + "\n");
		}
	}
}
class LCA {

	int[] precompute;
	int sq;
	HashMap<Integer, ArrayList<Integer>> tree;
	HashMap<Integer, Integer> parents;
	int[] heights;
	public LCA(HashMap<Integer, ArrayList<Integer>> tree, int root, int N, HashMap<Integer, Integer> parents) {
		this.tree = tree;
		this.parents = parents;
		heights = new int[N];
		Arrays.fill(heights, -1);
		int ht = heightDown(root);
		sq = ((int) Math.sqrt(ht));
		precompute = new int[N];
		Arrays.fill(precompute, -1);
		dfs(root, 0, root, 0);
	}

	public int heightDown(int node) {//height to bottom
		int max = 0;
		for (int a : tree.get(node)) {
			max = Math.max(max, heightDown(a));
		}
		return (1 + max);
	}

	public int height(int node) {//height to top
		if (heights[node] != -1) return heights[node];
		int max = 0;
		if (!parents.containsKey(node)) return 0;
		return heights[node] = (1 + height(parents.get(node)));
	}

	public void dfs(int cur, int lev, int last, int curIdx) { // dfs to fill precompute
		if (precompute[cur] == -1) precompute[cur] = last;
		int nextLev = lev + 1;
		int reach = (curIdx+1)*sq;
		int nextLast;
		int nextCurIdx;
		if (nextLev >= reach) {
			nextLast = cur;
			nextCurIdx = curIdx+1;
		} else {
			nextLast = last;
			nextCurIdx = curIdx;
		}

		for (int adj : tree.get(cur)) {
			dfs(adj, nextLev, nextLast, nextCurIdx);
		}
	}

	public int query(int u, int v) { // answers query using precompute
		int curu = u;
		int curv = v;
		// Find the lowest segment which has to have ancestor (or answer is precompute[u,v])
		while (precompute[curu] != precompute[curv]) {
			int heightu = height(curu);
			int heightv = height(curv);
			if (heightu > heightv) {
				curu = precompute[curu];
			} else {
				curv = precompute[curv];
			}
		}
		//Guaranteed to find ancestor in sqrt(H) time...
		while (curu != curv) {
			int heightu = height(curu);
			int heightv = height(curv);
			if (heightu > heightv) {
				curu = parents.get(curu);
			} else {
				curv = parents.get(curv);
			}
		}
		return curu;
	}
}