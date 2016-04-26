import java.util.*;

// combinatorics
class Skyscrapers {
	
	static long MOD = 1_000_000_007;
	static long[][] stirling;
	static long[][] choose;

	public static void main(String[] args) {
		stirling = new long[5001][5001];
		choose = new long[5001][5001];
		stirling[0][0] = 1;
		choose[0][0] = 1;
		for (int i = 1; i <= 5000; i++) {
			choose[i][0] = 1;
			for (int j = 1; j < i; j++) {
				stirling[i][j] = (stirling[i-1][j-1]%MOD+(i-1)*stirling[i-1][j]%MOD)%MOD;
				choose[i][j] = (choose[i-1][j-1]%MOD + choose[i-1][j]%MOD)%MOD;
			}
			choose[i][i] = 1;
			stirling[i][i] = 1;
		}
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			int L = sc.nextInt();
			int R = sc.nextInt();
			if (N == 0 && L == 0 && R == 0) break;
			long sum = 0;
			for (int i = 0; i < N; i++) {
				//put largest in box 2
				int left = i;
				int right = N-i-1;
				long s1 = (choose[N-1][left]%MOD*stirling[i][L-1]%MOD);
				sum += (s1%MOD*stirling[N-i-1][R-1]%MOD)%MOD;
				sum %= MOD;
			}
			System.out.println(sum % MOD);
		}
	}
}