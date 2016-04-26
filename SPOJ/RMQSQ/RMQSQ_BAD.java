import java.util.*;

public class RMQSQ_BAD {
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
		int Q = sc.nextInt();
		for (int i = 0; i < Q; i++) {
			int lo = sc.nextInt();
			int hi = sc.nextInt();
			int min = Integer.MAX_VALUE;
			for (int j = lo; j <= hi; j++) {
				min = Math.min(arr[j], min);
			}
			if (min == Integer.MAX_VALUE) throw new Exception();
			System.out.println(min);
		}
	}
}