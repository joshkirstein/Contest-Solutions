import java.util.*;
import java.math.*;
public class Seeds {

	static long cn;
	static long MOD = 1_000_000_007;
	static int N, K;
	static long[][] RHS;
	static long[][] matrix;
	static long inverseCn;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		int[] seeds = new int[N];
		for (int i = 0; i < N; i++) {
			seeds[i] = sc.nextInt();
		}
		int[] coef = new int[N];
		for (int i = 0; i < N; i++) {
			coef[i] = sc.nextInt();
		}
		cn = coef[N-1];

		RHS = new long[N][1];
		for (int i = N-1; i >=0; i--) {
			RHS[i][0] = seeds[N-1-i];
		}

		matrix = new long[N][N];
		for (int i = N-1; i >= 1; i--) {
			int row = N-i;
			matrix[row][N-1-i] = 1;
		}


		//a^(MOD-2) % MOD
		inverseCn = BigInteger.valueOf(cn).modPow(BigInteger.valueOf(MOD).subtract(BigInteger.valueOf(2)), BigInteger.valueOf(MOD)).longValue();

		//just have to do matrix[N-1] now:
		matrix[0][N-1] = inverseCn; // F(N)
		matrix[0][N-1] %= MOD;
		for (int i = N-1; i >= 1; i--) {
			long val = -coef[i-1];
			val %= MOD;
			if (val < 0) val += MOD;
			val %= MOD;
			val *= inverseCn;
			val %= MOD;
			matrix[0][N-1-i] = val;
		}


		calculate(K-N+1+N-1);
	}

	static void calculate(int value) {
		int power = value-N+1;
		if (power <= 0) {

			for (int i = 0; i < N; i++) {
				if (i == 0) {
					System.out.print(RHS[RHS.length-i-1][0]);
				} else {
					System.out.print(" " + RHS[RHS.length-i-1][0]);
				}
			}
			return;
		}
		long[][] pow = exp(matrix, power);
		//print(pow);
		long[][] hmm = multiply(pow, RHS, false);
		for (int i = 0; i < N; i++) {
			if (i == 0) {
				System.out.print(hmm[N-i-1][0]);
			} else {
				System.out.print(" " + hmm[N-i-1][0]);
			}
		}
	}

	static void print(long[][] mat) {
		for (long[] arr : mat) {
			System.out.println(Arrays.toString(arr));
		}
	}

	static long[][] exp(long[][] matrix, int pow) {
		if (pow == 0) return matrix;
		if (pow == 1) return matrix;
		if (pow % 2 == 0) {
			int div = pow/2;
			long[][] result = exp(matrix, div);
			return multiply(result, result, true);
		} else {
			return multiply(matrix, exp(matrix,pow-1), true);
		}
	}

	static long[][] multiply(long[][] mat1, long[][] mat2, boolean flag) {
		if (mat1[0].length != mat2.length) throw new RuntimeException("well fuck");
		long[][] res = new long[mat1.length][mat2[0].length];
		for (int i = 0; i < mat1.length; i++) {
			for (int j = 0; j < mat2[0].length; j++) {
				for (int k = 0; k < mat1[0].length; k++) {
					res[i][j] += ((mat1[i][k] % MOD)*(mat2[k][j]%MOD))%MOD;
					if (res[i][j] < 0) {
						res[i][j] += MOD;
						res[i][j] %= MOD;
					}
					res[i][j] %= MOD;
				}
			}
		}
		return res;
	}
}