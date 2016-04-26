import java.util.*;

public class RMQSQ {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
		int Q = sc.nextInt();
		RMQ rmq = new RMQ(arr);
		for (int i = 0; i < Q; i++) {
			System.out.println(arr[rmq.query(sc.nextInt(), sc.nextInt())]);
		}
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