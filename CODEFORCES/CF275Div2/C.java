import java.util.*;

public class C {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int[] perm = new int[N];
		for (int i = 1; i <= N; i++) {
			perm[i-1] = i;
		}
		int lo = 1, hi = N;
		boolean prev = false;
		while (K > 1) {
			if (prev) {
				if ((K - 2) < 1) break;
				K-=2;
			} else {
				if ((K - 1) < 1) break;
				K--;
			}
			prev = true;
			if (lo == hi) {
				emit(lo);
			} else {
				emit(lo, hi);
			}
			lo++;
			hi--;
		}
		if (K == 2) {
			for (int i = lo; i <= hi; i++) {
				emit(i);
			}
		} else {
			for (int i = hi; i >= lo; i--) {
				emit(i);
			}
		}
		System.out.println();
	}

	static void emit(int u) {
		if (first) {
			System.out.print(u);
			first = false;
		} else {
			System.out.print(" " + u);
		}
	}

	static boolean first = true;
	static void emit(int u, int v) {
		if (first) {
			System.out.print(u + " " + v);
			first = false;
		} else {
			System.out.print(" " + u + " " + v);
		}
	}
}