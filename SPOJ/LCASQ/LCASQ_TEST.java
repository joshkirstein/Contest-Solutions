
import java.util.*;

public class LCASQ_TEST {
	
	static Random rnd = new Random();
	static HashMap<Integer, ArrayList<Integer>> children = new HashMap<Integer, ArrayList<Integer>>();
	static int N;
	public static void main(String[] args) {
		N = 10000;
		emit(N);
		for (int i = 0; i < N; i++) {
			children.put(i, new ArrayList<Integer>());
		}
		int root = 0;
		Queue<Integer> q = new LinkedList<Integer>();
		HashSet<Integer> vis = new HashSet<Integer>();
		vis.add(root);
		q.add(root);
		while (!q.isEmpty()) {
			int cur = q.poll();
			int m = rnd.nextInt(10)+2;
			while (m-->0) {
				ArrayList<Integer> left = new ArrayList<Integer>();
				for (int i = 0; i < N; i++) {
					if (!vis.contains(i)) left.add(i);
				}
				if (left.size() == 0) break;
				int next = rnd.nextInt(left.size());
				int adj = left.get(next);
				vis.add(adj);
				children.get(cur).add(adj);
				q.add(adj);
			}
		}
		for (int i = 0; i < N; i++) {
			if (!vis.contains(i)) children.get(rnd.nextInt(N)).add(i);
		}
		for (int i = 0; i < N; i++) {
			emitChildren(i);
		}
		int Q = 10000;
		emit(Q);
		for (int i = 0; i < Q; i++) {
			int u = rnd.nextInt(N);
			int v = rnd.nextInt(N);
			emit(u, v);
		}
	}

	static void emit(int N) {
		System.out.println(N);
	}

	static void emit(int u, int v) {
		System.out.println(u + " " + v);
	}

	static void emitChildren(int u) {
		ArrayList<Integer> child = children.get(u);
		System.out.print(child.size());
		for (int i = 0; i < child.size(); i++) {
			System.out.print(" " + child.get(i));
		}
		System.out.println();
	}
}