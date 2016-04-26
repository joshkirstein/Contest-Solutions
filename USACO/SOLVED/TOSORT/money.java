
/*
ID: joshkir1
LANG: JAVA
TASK: money
*/
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class money {
    //TODO: Try bottom-up
    static int[] DENOM;
    static long[][] memo;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileInputStream("money.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("money.out"));
        //input processing output here
        int V = sc.nextInt();
        int N = sc.nextInt();
        DENOM = new int[V];
        for (int i = 0; i < V; i++) {
            DENOM[i] = sc.nextInt();
        }
        memo = new long[N + 1][DENOM.length];
        for (int i = 0; i <= N; i++) {
            Arrays.fill(memo[i], -1);
        }
        long ll = DP(N, 0);
        System.out.println(ll);
        out.append(ll + "\n");
        out.close();
    }
    
    public static long DP(int N, int K) {//N = money left, K = current idx of money
        if (N == 0) return 1;
        if (N < 0) return 0;
        if (K >= DENOM.length) return 0;
        if (memo[N][K] != -1) return memo[N][K];
        long total = DP(N, K + 1) + DP(N - DENOM[K], K);
        return memo[N][K] = total;
    }
}