import java.util.*;

public class RMQSQ_TEST {
	
	static Random rnd = new Random();
	public static void main(String[] args) throws Exception {
		int N = 100000;
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = rnd.nextInt(100000000);
		}
		int Q = 10000;
		emit(N);
		emit(arr);
		emit(Q);
		for (int i = 0; i < Q; i++) {
			int lo = 0;
			int hi = 99999;
			emit(lo, hi);
		}
	}

	public static void emit(int N) {
		System.out.println(N);
	}

	public static void emit(int x, int y) {
		System.out.println(x + " " + y);
	}

	public static void emit(int[] arr) {
		boolean first = true;
		for (int i = 0; i < arr.length; i++) {
			if (first) {
				System.out.print(arr[i]);
				first = false;
			} else {
				System.out.print(" " + arr[i]);
			}
		}
		System.out.println();
	}
}