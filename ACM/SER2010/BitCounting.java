import java.util.*;

class BitCounting {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			long l = sc.nextLong();
			long r = sc.nextLong();
			int X = sc.nextInt();
			if (l == 0 && r == 0 && X == 0) return;
			l -= 1;
			long answer = ans(r, X);
			if (l > 0) answer -= ans(l, X);
			//System.out.println("LO : " + l + " ANS: " + ans(l, X));
			//System.out.println("HI: " + r + " ANS: " + ans(r, X));
			System.out.println(answer);
		}
	}

	static int[] converge = new int[65];

	static {
		Arrays.fill(converge, -1);
		for (int i = 1; i <= 64; i++) {
			converge[i] = howLong(i);
			//System.out.println("NUMONES: " + i + " CONVERGES IN: " + converge[i]);
		}
	}

	public static int howLong(int ones) {
		if (ones == 1) return 0;
		else return 1+howLong(numOnes(ones));
	}

	public static int numOnes(long num) {
		int count = 0;
		while (num != 0) {
			if ((num & 1) == 1) count++;
			num = num >> 1;
		}
		return count;
	}

	static long[][] choo = new long[65][65];
	static {
		for (long[] D : choo) Arrays.fill(D, -1);
	}

	public static long ans(long upper, int k) {
		if (k == 0) return 1;//always only 1...
		long sum = 0;
		int ones = numOnes(upper);
		for (int i = 1; i <= 64; i++) {
			if (converge[i]+1 == k) {
				//System.out.println("I: " + i + " CONVERGES IN: " + converge[i]);
				//How many numbers below or equal to upper have i 1s...
				sum += countBits(i, upper);
			}
		}
		if (k == 1) sum--;//if k ==1 1 shouldnt be counted
		//the +1 (effectively saying we're transforming some number to N_i) doesn't happen
		//with 1 because 1 is done and doesn't require transform.
		//if (converge[ones] <= k) sum++;
		return sum;
	}
	
	public static long countBits(int ones, long upper) {
		//number of numbers with exactly 'ones' ones less than or equal to upper.
		if (ones == 0) return 1;
		long i = 0;
		long maxIdx = -1;
		while (i < 64) {
			if ((upper & ((long) (1L << i))) != 0) {
				//System.out.println("FOUND: " + i);
				maxIdx = i;
			}
			i++;
		}
		if (ones > maxIdx+1) {
			return 0;
		}//more ones than spots...can't do it :o

		if (upper == 0 && ones > 0) return 0;//not possible
		if (maxIdx == -1) return 0;//Upper == 0
		return choose((int)maxIdx, ones) + countBits(ones-1, upper-(1L<<maxIdx)); // Unset most significant 1 (act like we're using it)
	}
	
	public static long choose(int n, int k) {
		if (k > n) return 0;
		if (n == 0) return 0;
		if (k == 0) return 1;
		if (n == k) return 1;
		if (choo[n][k] != -1) return choo[n][k];
		int add = 0;
		return choo[n][k] = (add + choose(n-1, k-1) + choose(n-1, k));
	}
}