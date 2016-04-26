import java.util.*;

// prefix sum + prime numbers
class BeautifulMountains {

	static boolean isPrime(int x) {
		if (x == 1) return false;
		if (x == 0) return false;
		if (x == 2) return true;
		if (x % 2 == 0) return false;
		for (int i = 3; i*i <= x; i += 2) {
			if (x % i == 0) return false;
		}
		return true;
	}

	static boolean[] prime;
	static int[] list;
	static int lastPrime = 0;
	static {
		prime = new boolean[30050];
		list = new int[30050];
		Arrays.fill(prime, true);
		prime[0] = false;
		prime[1] = false;
		for (int i = 2; i < 30050; i++) {;
			if (prime[i]) {
				list[lastPrime++] = i;
				for (int j = i+i; j < 30050; j += i) {
					prime[j] = false;
				}
			}
		}
	}

	static long[] leftsum, rightsum, moveright, moveleft;

	static long l(int x) {
		if (x < 0) return 0;
		return moveleft[x];
	}

	static long r(int x) {
		if (x >= leftsum.length) return 0;
		return moveright[x];
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == 0) break;
			long[] height = new long[N];
			for (int i = 0; i < N; i++) {
				height[i] = sc.nextInt();
			}
			leftsum = new long[N];
			moveleft = new long[N];
			moveright = new long[N];
			rightsum = new long[N];
			for (int i = 0; i < N; i++) {
				leftsum[i] = height[i];
				leftsum[i] += i-1>=0?leftsum[i-1]:0;
				if (i > 0) {
					moveleft[i] = moveleft[i-1];
					moveleft[i] += leftsum[i-1];
				}
				int itemp = i;

				i = N-i-1;

				rightsum[i] = height[i];
				rightsum[i] += i+1<N?rightsum[i+1]:0;
				if (i+1 < N) {
					moveright[i] = moveright[i+1];
					moveright[i] += rightsum[i+1];
				}

				i = itemp;
			}
			//Case 23
			//case 32
			//case 232
			//case p
			long best = Long.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				best = Math.min(best, l(i)+r(i));
				//case p
				for (int j = 0; j <= lastPrime && i+list[j] < N; j++) {
					int p = list[j];
					//if (prime[p]) {
						{
						int first = i;
						int second = i+p;
						long cost = l(first)+r(second);
						long mid = first+(second-first)/2;
						int third = second;
						second = first;
						cost += moveright[second]-(moveright[(int)mid]+(Math.abs(mid-second))*rightsum[(int)mid+1]);
						cost += moveleft[third]-(moveleft[(int)mid+1]+(Math.abs((mid+1)-third))*leftsum[(int)mid]);
						best = Math.min(cost, best);
					}
				//case 23
				if (i + (2+p) < N && prime[2+p]) {
					int first = i;
					int second = i+2;
					int third = second+p;
					long cost = l(first)+r(third);
					cost += height[i+1];
					long mid = second+(third-second)/2;
					cost += moveright[second]-(moveright[(int)mid]+(Math.abs(mid-second))*rightsum[(int)mid+1]);
					cost += moveleft[third]-(moveleft[(int)mid+1]+(Math.abs((mid+1)-third))*leftsum[(int)mid]);

					best = Math.min(cost, best);
				}
				//case 32
				if (i + (2+p) < N && prime[2+p]) {
					int first = i;
					int second = i+p;
					int third = second+2;
					long cost = l(first)+r(third);
					cost += height[second+1];

					second = i;
					third = i+p;

					long mid = second+(third-second)/2;
					cost += moveright[second]-(moveright[(int)mid]+(Math.abs(mid-second))*rightsum[(int)mid+1]);
					cost += moveleft[third]-(moveleft[(int)mid+1]+(Math.abs((mid+1)-third))*leftsum[(int)mid]);

					best = Math.min(cost, best);
				}
				//case 232
				if (i+(2+p+2) < N && prime[4+p] && prime[2+p]) {
					int first = i;
					int second = i+2;
					int third = second+p;
					int fourth = third+2;
					long cost = l(first)+r(fourth)+height[first+1]+height[third+1];

					long mid = second+(third-second)/2;
					cost += moveright[second]-(moveright[(int)mid]+(Math.abs(mid-second))*rightsum[(int)mid+1]);
					cost += moveleft[third]-(moveleft[(int)mid+1]+(Math.abs((mid+1)-third))*leftsum[(int)mid]);
					best = Math.min(cost, best);


				}
			//}
			}
			}
			System.out.println(best);
		}
	}
}