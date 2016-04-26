/*
ID: joshkir1
LANG: JAVA
TASK: theme
*/

import java.util.*;
import java.io.*;

public class theme {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader("theme.in"));
        PrintWriter out = new PrintWriter(new FileWriter("theme.out"));
        
        N = sc.nextInt();
        notes = new int[N];
        for (int i = 0; i < N; i++) notes[i] = sc.nextInt();

        int max = 0;
        pre();
        short[][] prev = new short[N][2];
        short[][] next = new short[N][2];
        boolean first = true;

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = 0; k < 2; k++) {
                    if (first) {
                        next[j][k] = 0;
                    } else {
                        prev[j][k] = 0;
                    }
                    if (k == 0) {
                        //equal only
                        if (notes[i] == notes[j]) {
                            short best = 1;
                            if (i > 0 && j > 0) {
                                if (first) {
                                    best += prev[j-1][k];
                                } else {
                                    best += next[j-1][k];
                                }
                            }
                            if (i+best<=j) {
                                if (first) {
                                    next[j][k] = best;
                                } else {
                                    prev[j][k] = best;
                                }
                                max = Math.max(max, best);
                            }
                        }
                    } else {
                        int diff = notes[i]-notes[j];
                        short best = 1;
                        if (i > 0 && j > 0) {
                            int diff2 = notes[i-1]-notes[j-1];
                            if (diff == diff2) {
                                if (first) {
                                    best += prev[j-1][k];
                                } else {
                                    best += next[j-1][k];
                                }
                            }
                        }
                        if (i+best<=j) {
                            if (first) {
                                next[j][k] = best;
                            } else {
                                prev[j][k] = best;
                            }
                            max = Math.max(max, best);
                        }
                    }
                }
            }
            first = !first;
        }
        post();

        if (max < 5) max = 0;
        out.println(max);
        System.out.println(max);

        out.close();
    }
    static int N;
    static int[] notes;
    static long ms, cur;
    public static void pre() {
        ms = System.currentTimeMillis();
    }
    public static void post() {
        cur = System.currentTimeMillis();
        System.out.println("TOOK: "+ (cur-ms) + "ms.");
    }
}
