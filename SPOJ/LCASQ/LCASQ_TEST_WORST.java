
import java.util.*;

public class LCASQ_TEST_WORST {
	
	static Random rnd = new Random();
	static HashMap<Integer, ArrayList<Integer>> children = new HashMap<Integer, ArrayList<Integer>>();

	public static void main(String[] args) {
		int N = 3000;
		int Q = 10000;
		for (int i = 0; i < N-1; i++) {
			ArrayList<Integer> child = new ArrayList<Integer>();
			child.add(i+1);
			children.put(i, child);
		}
		children.put(N-1, new ArrayList<Integer>());
		emit(N);
		for (int i = 0; i < N; i++) {
			emitChildren(i);
		}
		emit(Q);
		for (int i = 0; i < Q; i++) {
			emit(0, N-1);
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