import java.util.*;

class LegoBlocks {
    
    static long[][] CLIPMEMO;
    static long[][] NOCLIPMEMO;
    static long[] NUMWAYS;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int z = 0; z < cases; z++) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            CLIPMEMO = new long[N+1][M+1];
            NOCLIPMEMO = new long[N+1][M+1];
            NUMWAYS = new long[M+1];
            Arrays.fill(NUMWAYS, -1);
            for (long[] arr : CLIPMEMO) Arrays.fill(arr, -1);
            for (long[] arr : NOCLIPMEMO) Arrays.fill(arr, -1);

            long ans = ((numWaysNoclip(N, M)%1000000007)-(numWaysClip(N, M)%1000000007));
            if (ans < 0) ans += 1000000007;
            System.out.println(ans%1000000007);
        }
    }

    public static long numWaysClip(int H, int W) {
        if (CLIPMEMO[H][W] != -1) return CLIPMEMO[H][W];
        if (H == 0) return 0;
        if (W == 0) return 0;
        long sum = 0;
        for (int i = 1; i < W; i++) {
            //Cut from i | i+1 i+2 ...
            long numWaysHalf1 = numWaysNoclip(H, i)%1000000007;
            long numWaysHalf2 = numWaysNoclip(H, W-i)%1000000007;
            long numWaysClip = numWaysClip(H, i) %1000000007;
            long numWaysNoclip = numWaysNoclip(H, W-i) %1000000007;
            long res1 = (numWaysHalf1*numWaysHalf2) %1000000007;
            long res2 = (numWaysClip*numWaysNoclip)%1000000007;
            sum += (res1%1000000007-res2%1000000007)%1000000007;
            sum %= 1000000007;
            //sum += ((((numWaysHalf1%1000000007)*(numWaysHalf2%1000000007))%1000000007)-((numWaysClip(H, i)%1000000007) * (numWaysNoclip(H, W-i)%1000000007))%1000000007+1000000007)%1000000007;
        }
        return CLIPMEMO[H][W] = (sum+1000000007) % 1000000007;
    }

    public static long numWaysNoclip(int H, int W) {
        if (NOCLIPMEMO[H][W] != -1) return NOCLIPMEMO[H][W];
        if (H == 0) return 0;
        else {
            return NOCLIPMEMO[H][W] = (modPow(numWays(W), H)); // MOD POW.
        }
    }

    public static long modPow(long base, long exponent) {
        long res = 1;
        for (long i = 0; i < exponent; i++) {
            res *= base % 1000000007;
            res %= 1000000007;
        }

        //System.out.println("BASE: " + base + " EXPONENT: " + exponent + " RES: " + (res%1000000007));
        return res % 1000000007;
    }
    
    public static long numWays(int number) {
        if (NUMWAYS[number] != -1) return NUMWAYS[number];
        if (number == 0) return 1;
        long sum = 0;
        if (number >= 4) {
            sum += 1*numWays(number-4)%1000000007;
            sum %= 1000000007;
        }
        if (number >= 3) {
            sum += 1*numWays(number-3)%1000000007;
            sum %= 1000000007;
        }
        if (number >= 2) {
            sum += 1*numWays(number-2)%1000000007;
            sum %= 1000000007;
        }
        if (number >= 1) {
            sum += 1*numWays(number-1)%1000000007;
            sum %= 1000000007;
        }
        return NUMWAYS[number] = sum%1000000007;
    }
}