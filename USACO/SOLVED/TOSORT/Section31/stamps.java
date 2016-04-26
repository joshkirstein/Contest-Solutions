package Section31;

/*
ID: joshkir1
LANG: JAVA
TASK: stamps
*/
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class stamps {
    
    static short[] gen;
    static int[] stamps;
    //static boolean[][][] vis;
    //static boolean[][] vis;
    
    //THIS WORKS
    //attempts:
    //boolean[K+1][stamps.length][K*maxStamp+1] = if we've visited this state before,
    //populating the array is self-explanatory...top-down approach used...
    //Didn't work, array get's TOO big...even the amount of time to populate is TOO long.
    //next: boolean[K*maxStamp+1]...have we seen a sum?...thought i might be able to populate in order
    //no...last idea:
    //
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("stamps.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("stamps.out"));
        int K = sc.nextInt();
        int N = sc.nextInt();
        stamps = new int[N];
        for (int i = 0; i < N; i++) {
            stamps[i] = sc.nextInt();
        }
        Arrays.sort(stamps);//Might already be sorted...
        int max = K * stamps[N - 1];
        gen = new short[max + 1];//amt used...
        //vis = new boolean[K + 1][stamps.length][K * stamps[N - 1] + 2];
        //start(K, 0, 0);
        //vis = new boolean[K + 1][max + 1];//vis[K][sum] = have we been to state with K stamps left @ sum
        //base case: vis[0..K][0] = true
        /*for (int i = 0; i <= K; i++) {
            vis[i][0] = true;
        }*/
        /*for (int i = 0; i <= max; i++) {
            for (int k = 0; k < stamps.length; k++) {
                int stamp = stamps[k];
                for (int j = 1; j <= K; j++) {
                    if (vis[j][i]) {
                        int newSum = i + stamp;
                        gen[newSum] = true;
                        vis[j - 1][newSum] = true;
                    }
                }
            }
        }*/
        //start(K, 0, 0);
        gen[0] = 0;
        //for (int k = K; k > 0; k--) {//amt we can take...
            for (int i = 0; i <= max; i++) {//cur sums
                if (gen[i] < K) {//done the sum already
                    for (int j = 0; j < stamps.length; j++) {
                        int stamp = stamps[j];//stamp to try...
                        int newSum = i + stamp;
                        if (newSum > max) continue;
                        if (gen[newSum] == 0) gen[newSum] = (short) (gen[i] + 1);
                        else gen[newSum] = (short) Math.min(gen[newSum], gen[i] + 1);
                    }
                }
            }
        //}
        for (int i = 1; i < gen.length; i++) {
            if (gen[i] == 0) {
               // System.out.println(Arrays.toString(gen));
                out.append(i - 1 + "\n");
                System.out.println(i - 1);
                break;
            }
            if (i == gen.length - 1) {
                //System.out.println(Arrays.toString(gen));
                //System.out.println(gen[i]);
                out.append(i + "\n");
                System.out.println(i);
                break;
            }
        }
        out.close();
    }
}