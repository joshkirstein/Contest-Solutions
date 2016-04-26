
/*
ID: joshkir1
LANG: JAVA
TASK: nocows
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class nocows {
    
    static int[][] DP_MAIN;
    static int[][] DP_SMALL;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("nocows.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("nocows.out"));
        //input processing output here
        String[] spl = sc.readLine().split(" ");
        int N = Integer.parseInt(spl[0]);
        int K = Integer.parseInt(spl[1]);
        if (N % 2 == 0) {
            //Not possible.
            out.append("0\n");
        } else {
            DP_MAIN = new int[N + 1][K + 1];
            DP_SMALL = new int[N + 1][K + 1];
            for (int i = 0; i <= N; i++) {
                Arrays.fill(DP_MAIN[i], -1);
                Arrays.fill(DP_SMALL[i], -1);
            }
            out.append((DP(N, K)) + "\n");
        }
        out.close();
    }
    
    public static int DP(int n, int k) {//n is odd...
        int N = n - 1;
        if (n % 2 == 0) return 0;//not possible..
        if (DP_MAIN[n][k] != -1) return DP_MAIN[n][k];
        if (k == 1) {
            //System.out.println("DP(" + n + "," + k + ") = " + (n == 1 ? 1 : 0));
            return N == 0 ? 1 : 0;
        }//Base case...if N > 1, we have too many nodes to make a tree of depth 1...:)
        //using a node for this current pos..
        int total = 0;
        for (int i = 1; i <= N; i += 2) {   
            //always an even amount left. Only not at first DP call.
            total += DP(i, k - 1) * DP(N - i, k - 1);//have to have at least one DP call with k - 1, it guarantees we have that depth...
            total %= 9901;
            total += DP(i, k - 1) * DP_SMALL(N - i, k - 2);
            //(k-2) because it is STRICTLY smaller, because if it wasnt, we'd have some inclusion exclusion problems
            //with DP
            total %= 9901;
            total += DP_SMALL(i, k - 2) * DP(N - i, k - 1);
            total %= 9901;
        }
        //System.out.println("DP(" + n + "," + k + ") = " + total);
        return DP_MAIN[n][k] = total;
    }
    
    public static int DP_SMALL(int n, int k) {//n is odd
        if (n % 2 == 0) return 0;
        int N = n - 1;
        if (DP_SMALL[n][k] != -1) return DP_SMALL[n][k];
        if (N == 0 && k >= 1) {
            //System.out.println("DP(" + n + "," + k + ") = 1");
            return 1;
        }
        if (N < 0 || k < 1) {
            //System.out.println("DP(" + n + "," + k + ") = 0");
            return 0;
        }//instead of returning on depth, return on nodes too! (= 0) note, depth shouldnt reach less than 0
        int total = 0;
        for (int i = 1; i <= N; i++) {
            total += DP_SMALL(i, k - 1) * DP_SMALL(N - i, k - 1);
            total %= 9901;
        }
        //System.out.println("DP_SMALL(" + n + "," + k + ") = " + total);
        return DP_SMALL[n][k] = total;
    }
}