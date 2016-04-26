import java.io.*;
import java.util.*;
import java.math.*;

public class Solution2 {

    static int MOD = 100003;
    static long N, K;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        while (cases-->0) {
            N = sc.nextLong();
            K = sc.nextLong();
           // System.out.println(nCk(N, K));
           // System.out.println("Hello");
            //System.out.println(Arrays.toString(coeff(N, MOD)));
            //System.out.println(Arrays.toString(coeff(K, MOD)));
            System.out.println(nmod(N-K+1, K, MOD));
        }
    }

    public static long nmod(long n, long k, long p) {
        int[] coefN = coeff(n, p);
        int[] coefK = coeff(k, p);
        long res = 1;
        for (int i = 0; i < coefN.length; i++) {
            int N = coefN[i];
            int K = coefK[i];
            if (N != 0 && K != 0 && N >= K) {
                res *= nCk(N, K);
                res %= p;
            } else {
                if (N < K) {
                    res = 0;
                    break;
                }
            }
        }
        return res % p;
    }

    public static long nCk(long n, long k) {
        long top = factP(n, MOD);
        long bot = factP(k, MOD);
        long botOther = factP(n-k, MOD);
        long botBot = ((bot%MOD)*(botOther%MOD))%MOD;
        BigInteger mul = BigInteger.valueOf(top);
        BigInteger inv = BigInteger.valueOf(botBot).modInverse(BigInteger.valueOf(MOD));
        long val = inv.longValue();
        return (top*val)%MOD;
    }

    public static long factP(long n, long p) {
        long val = 1;
        for (long i = 1; i <= n; i++) {
            val = val * i;
            val %= p;
        }
        //System.out.println(n + "! % p = " + val);
        return val;
    }

    public static int[] coeff(long n, long p) {
        int i = 0;
        int[] arr = new int[20];
        while (true) {
            long div = n / p;
            long rem = n % p;
            arr[i++] = (int) rem;
            n = div;
            //System.out.println("DIV: " + div + " REM: "+ rem);
            if (rem == 0) break;
        }
        return arr;
    }
}