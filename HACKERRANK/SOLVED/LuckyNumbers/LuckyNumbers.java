import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class LuckyNumbers {

	static long lucky(long a, int sum, int sqsum) {
		//if (a == 0) return 0;
		int dig = numDigits(a);
		long res = 0;
		int lastDig = getNthDigit(a, dig);
		//System.out.println("CALL");
		for (int i = 0; i < lastDig; i++) {
			res += dp(sum+i, sqsum+i*i, dig-1);
		}
		//System.out.println("RES: " + res + " FOR " + a + " SUM: "+ sum + " sqsum: " + sqsum);
		if (dig != 1)
			res += lucky(((long)a%((long)Math.pow(10, dig-1))), sum+lastDig, sqsum+lastDig*lastDig);
		else {
			return res+(good(sum+lastDig, sqsum+lastDig*lastDig) ? 1 : 0);
		}



		return res;
	}

	static long[][][] dp;
	static {
		dp = new long[200][2000][20];
		for (long[][] arr : dp) {
			for (long[] ar : arr) {
				Arrays.fill(ar, -1);
			}
		}
	}

	static long dp(int sum, int sqsum, int digleft) {
		if (dp[sum][sqsum][digleft] != -1) return dp[sum][sqsum][digleft];
		if (digleft == 0) {
			if (good(sum, sqsum)) {
				//System.out.println("YES DPSUM: " + sum + " SQSUM: " + sqsum + " DIGLEFT: " + digleft);
				return dp[sum][sqsum][digleft] = 1;
			} else {
				//System.out.println("NO DPSUM: " + sum + " SQSUM: " + sqsum + " DIGLEFT: " + digleft);
				return dp[sum][sqsum][digleft] = 0;
			}
		} else {
			long hold = 0;
			for (int i = 0; i <= 9; i++) {
				hold += dp(sum+i, sqsum+i*i, digleft-1);
			}
			return dp[sum][sqsum][digleft] = hold;
		}
	}

	static boolean good(int sum, int sqsum) {
		return isPrime(sum) && isPrime(sqsum);
	}

	static boolean isPrime(int num) {
		if (num == 1 || num == 0) return false;
		if (num == 2) return true;
		int sq = (int) Math.sqrt(num) + 1;
		for (int i = 2; i <= sq; i++) {
			if (num % i == 0) return false;
		}
		return true;
	}

	static long pow(long num, long exp) {
		//System.out.println("NUM: " + num + " EXP: " + exp);
		if (true) return (long)Math.pow(num, exp);
		if (exp == 0) return 1;
		if (exp % 2 == 0) {
			long exp2 = exp/2;
			long val = pow(num, exp2);
			return val*val;
		} else {
			return pow(num, exp-1)*num;
		}
	}

    static long luckyNumbers(long a,long b) {
    	/*for (long c = a-1; c <= b; c++) {
    		System.out.println("LUCKY(" + c + "): " + lucky(c,0,0));
    	}*/
    	//System.out.println("B: " + lucky(120,0,0));
    	//System.out.println("B: " + lucky(121,0,0));

    	//System.out.println("A-1: " + lucky(a-1,0,0));
        return lucky(b, 0, 0) - (a == 1 ? 0 : lucky(a-1, 0, 0));
    }



	static int numDigits(long num) {
		if (num == 0) return 1;
		return ((int)Math.log10(num))+1;
	}

	static int getNthDigit(long num, int n) {
		int dig = (int) ((num % ((pow(10, n)))) / (pow(10, n-1)));
		return dig;
	}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long res;
        
        int _a_size = Integer.parseInt(in.nextLine());
        long _a,_b;
        for(int _a_i = 0; _a_i < _a_size; _a_i++) {
            String next = in.nextLine();
            String[] next_split = next.split(" ");
            _a=Long.parseLong(next_split[0]);
            _b=Long.parseLong(next_split[1]);
           res = luckyNumbers(_a,_b);
           System.out.println(res);
        }
    }
}
