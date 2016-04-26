import java.util.*;

public class Smith {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		ArrayList<Integer> primes = new ArrayList<Integer>();
		int sqrt = (int) Math.sqrt(num);
		for (int i = 2; i <= sqrt; i++) {
			if (isPrime(i)) primes.add(i);
		}
		int sum = sumDig(num);
		int sum2 = 0;
		int val = num;
		int idx = 0;
		while (idx < primes.size()) {
			int prime = primes.get(idx++);
			if (val % prime != 0) continue;
			while (val % prime == 0) {
				val /= prime;
				sum2 += sumDig(prime);
			}
		}
		if (val != 1) sum2 += sumDig(val);
		if (sum ==sum2) {
			System.out.println("1");
		} else {
			System.out.println("0");
		}
	}

	static boolean isPrime(int i) {
		if (i == 1 || i == 0) return false;
		if (i == 2) return true;
		if (i % 2 == 0) return false;
		int sq = (int) Math.sqrt(i);
		for (int j = 3; j <= sq; j+=2) {
			if (i % j == 0) return false;
		}
		return true;
	}

	static int sumDig(int i) {

		String s = String.valueOf(i);
		int sum = 0;
		for (char c : s.toCharArray()) sum += Integer.parseInt(c+"");
		return sum;
	}
}