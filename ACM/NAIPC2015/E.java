import java.util.*;
public class E {


	static boolean DEBUG = false;


	static int[] best = new int[1000001];
	static boolean[] isNotPrime = new boolean[1000001];

	static {
		long ms = System.currentTimeMillis();
		isNotPrime[0] = true;
		isNotPrime[1] = true;
		for (int i = 2; i < isNotPrime.length; i++) {
			if (isNotPrime[i]) continue;
			best[i] = i;
			for (int j = 2; j*i < isNotPrime.length; j++) {
				isNotPrime[i*j] = true;
				best[i*j] = Math.max(best[i*j], i);
			}
		}
		debug("TOOK " + (System.currentTimeMillis()-ms) + " ms.");
	}

	static void debug(String s) {
		if (!DEBUG) return;
		System.out.println(s);
	}
	
	static int[] arr;
	static int N, K;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = sc.nextInt();
		}
		int lo = 2;
		int hi = 1000001;
		int max = 0;
		while (lo <= hi) {
			int mid = lo + (hi-lo)/2;
			boolean works = false;

			int ma = go(0, K-1, mid);
			works = ma >= mid;
			if (works) {
				max = Math.max(max, mid);
				lo = mid+1;
			} else {
				hi = mid-1;
			}
		}
		System.out.println(max);
	}
	static int go(int start, int left, int mi) {
		if (start >= N && left == 0) return Integer.MAX_VALUE;
		if (start >= N && left > 0) return 0;
		if (left == 0) {
			int gcd = arr[start];
			for (int i = start; i < N; i++) {
				gcd = GCD(gcd, arr[i]);
			}
			return best[gcd];
		}
		int bes = 0;
		int gcd = arr[start];
		if (best[gcd] < mi) return 0;
		int i = start;
		while (true) {
			if ((N-i) <= left) break;
			i++;
			int tempgcd = GCD(gcd, arr[i]);
			if (best[tempgcd] < mi) {
				i--;
				break;
			}
			gcd = tempgcd;
		}
		return Math.min(best[gcd], go(i+1, left-1, mi));
	}
		

	static int GCD(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return GCD(b, a%b);
    }
}