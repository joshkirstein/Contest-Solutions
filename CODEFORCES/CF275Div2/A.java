import java.util.*;

public class A {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long l = sc.nextLong(), r = sc.nextLong();
		for (long i = l; i <= r; i++) {
			for (long j = i+1; j <= r; j++) {
				for (long k = j+1; k <= r; k++) {
					if (i != j && j != k && i != k) {
						if (gcd(i, j)==1 && gcd(j, k) == 1 && gcd(i, k) != 1) {
							System.out.println(i + " " + j + " " + k);
							return;
						}
					}
				}
			}
		}
		System.out.println("-1");
	}

	public static long gcd(long a, long b) {
		if (b == 0) return a;
		return gcd(b, a%b);
	}
}