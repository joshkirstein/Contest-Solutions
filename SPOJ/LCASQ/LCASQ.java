import java.util.*;

public class LCASQ {
	
	static HashMap<Integer, ArrayList<Integer>> tree;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cases = 1;
		StringBuilder br = new StringBuilder("");
		while (cases-->0) {
			int N = sc.nextInt();
			tree = new HashMap<Integer, ArrayList<Integer>>();
			for (int i = 0; i < N; i++) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				tree.put(i, list);
				int amt = sc.nextInt();
				for (int j = 0; j < amt; j++) {
					int val = sc.nextInt();
					list.add(val);
				}
			}
			int root = 0;
			LCA2 lca = new LCA2(tree, root, N);
			RMQ rmq = new RMQ(lca.L);
			int Q = sc.nextInt();
			for (int i = 0; i < Q; i++) {
				int u = lca.H[sc.nextInt()];
				int v = lca.H[sc.nextInt()];
				if (v < u) {
					int temp = v;
					v = u;
					u = temp;
				}
				int val = lca.E[rmq.query(u, v)];

				br.append(val + "\n");
			}
		}
		System.out.print(br.toString());
	}
}
class LCA2 {

	public HashMap<Integer, ArrayList<Integer>> tree;
	public int[] L, E, H;
	public RMQ rmq;
	public int counter;
	public LCA2(HashMap<Integer, ArrayList<Integer>> tree, int root, int N) {
		this.tree = tree;
		counter = 0;
		L = new int[2*N+1];
		E = new int[2*N+1];
		H = new int[N];
		Arrays.fill(H, -1);
		rmq = new RMQ(L);
		dfs(root, 0);
	}

	public void dfs(int node, int depth) {
		counter++;
		if (H[node] == -1) {
			H[node] = counter;
		}
		L[counter] = depth;
		E[counter] = node;
		for (Integer adj : tree.get(node)) {
			dfs(adj, depth+1);
			counter++;
			L[counter] = depth;
			E[counter] = node;
		}
	}

	public int query(int u, int v) {
		int from = H[u];
		int to = H[v];
		if (from > to) {
			int val = to;
			to = from;
			from = val;
		}
		int idxOf = rmq.query(from, to); // idx of min L element
		// return the node that has the smallest level between u and v
		return E[idxOf];
	}
}
class RMQ {
	private int[] array;
	private int[] sqrt;

	public RMQ(int[] array) {
		this.array = array;
		int sq = (int)  Math.ceil(Math.sqrt(array.length));
		this.sqrt = new int[sq+1];
		int j = 0;
		for (int i = 0; i < array.length; i += sq, j++) {
			int end = Math.min(array.length-1, i+sq-1);
			int min = Integer.MAX_VALUE;
			int minIdx = -1;
			for (int k = i; k <= end; k++) {
				if (array[k] < min) {
					min = array[k];
					minIdx = k;
				}
			}
			sqrt[j] = minIdx;
		}
	}

	public int query(int l, int r) {
		int sq = (int)  Math.ceil(Math.sqrt(array.length));
		int j = 0;
		int min = Integer.MAX_VALUE;
		int minIdx = -1;
		for (int i = 0; i < array.length; i += sq, j++) {
			int lo = i;
			int hi = i+sq-1;
			if (lo >= l && hi <= r) {
				int idx = sqrt[j];
				if (array[idx] < min) {
					min = array[idx];
					minIdx = idx;
				}
			} else {
				int x=-1, y=-1;
				if (l >= lo && r > hi) {
					x = l;
					y = r;
				} else if (r <= hi && l < lo) {
					x = lo;
					y = r;
				} else if (l >= lo && r <= hi) {
					x = l;
					y = r;
				}
				if (x != -1 && y != -1) {
					for (int k = x; k <= y; k++) {
						if (array[k] < min) {
							min = array[k];
							minIdx = k;
						}
					}
				}
			}
		}
		return minIdx;
	}
}