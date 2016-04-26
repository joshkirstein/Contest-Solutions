package Section31;


/*
ID: joshkir1
LANG: JAVA
TASK: humble
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class humble {
    
    static int cur = 0;
    static int K, N;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("humble.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("humble.out"));
        //input processing output here
        String[] spl = sc.readLine().split(" ");
        K = Integer.parseInt(spl[0]);
        N = Integer.parseInt(spl[1]);
        int[] P = new int[K];
        spl = sc.readLine().split(" ");
        for (int i = 0; i < K; i++) {
            P[i] = Integer.parseInt(spl[i]);
        }
        //Arrays.sort(P);
        int[] VAL = new int[N+1];//too slow how to do
        int[] SMALL = new int[K];//keeps track of the idx of the next humble number to multiple P[k] by
        //a humble number is a multiplication of a prime and another humble number (assuming 1 is a H.N)
        //Arrays.fill(SMALL, 1);
        Arrays.fill(VAL, -1);
        VAL[0] = 1;
        for (int i = 1; i <= N; i++) {
            /*MAIN:
            for (int j = i - 1; j >= 0; j--) {
                for (int k = K - 1; k >= 0; k--) {
                    int val = VAL[j] * P[k];
                    if (val > VAL[i - 1]) {
                        if (VAL[i] == -1) VAL[i] = val;
                        else VAL[i] = Math.min(VAL[i], val);
                    } else if (VAL[j] * P[K-1] < VAL[i - 1]) break MAIN;
                 }
            }*/
            //System.out.println("INDEX: " + i);
            //System.out.println(Arrays.toString(SMALL));
            int min;
            do {
                min = Integer.MAX_VALUE;
                int toChange = -1;
                for (int k = 0; k < K; k++) {
                    int val = VAL[SMALL[k]] * P[k];
                    if (val < min) {
                        min = val;
                        toChange = k;
                    }
                }
                //if (toChange == -1) return;
                SMALL[toChange]++;
            } while (min <= VAL[i - 1]);//increment the idx (since there are multiple ways to get some humble numbers)
            VAL[i] = min;
        }
        //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 24, 25, 27]
        //0   1  2  3  4  5  6  7  8  9
        //[1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 12, 12, 12, 14, 14, 15, 15, 16]
        //System.out.println(Arrays.toString(VAL));
        //System.out.println(VAL[N]);
        out.append(VAL[N] + "\n");
        out.close();
    }
}