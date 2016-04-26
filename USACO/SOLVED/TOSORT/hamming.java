/*
 ID: joshkir1
 LANG: JAVA
 TASK: hamming
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class hamming {
    
    static int B;
    static BufferedWriter out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("hamming.in"));
        out = new BufferedWriter(new FileWriter("hamming.out"));
        String[] spl = sc.readLine().split(" ");
        int N = Integer.parseInt(spl[0]);
        B = Integer.parseInt(spl[1]);
        int D = Integer.parseInt(spl[2]);
        int min = 0;//always
        int max = (int) Math.pow(2, B);
        boolean[] vis = new boolean[max];
        dfs(vis, 0, D, N);
        out.close();
    }
    
    static boolean stop = false;
    public static void dfs(boolean[] vis, int amt, final int D, final int N) throws IOException {
        if (stop) return;
        if (amt == N) {
            boolean first = true;
            int x = 0;
            int numm = 0;
            for (int i = 0; i < vis.length; i++) {
                if (vis[i]) {
                    if (first) {
                        out.append(i + "");
                        numm++;
                        first = false;
                    } else {
                        out.append(" " + i);
                        numm++;
                    }
                    x++;
                }
                if (x == 10 && numm != amt) {
                    x = 0;
                    out.append("\n");
                    first = true;
                }
            }
            out.append("\n");
            stop = true;
            return;
        } else {
            MAIN:
            for (int i = 0; i < vis.length; i++) {
                if (!vis[i]) {
                    for (int j = 0; j < vis.length; j++) {
                        if (vis[j]) {
                            if (!dist(i, j, D)) {
                                continue MAIN;
                            }
                        }
                    }
                    //dist D from all currently visited
                    vis[i] = true;
                    dfs(vis, amt + 1, D, N);
                    vis[i] = false;
                }
            }
        }
    }

    public static boolean dist(int one, int two, int dist) {
        //System.out.println(Integer.toBinaryString(one));
        //System.out.println(Integer.toBinaryString(two));
        StringBuilder bOne = new StringBuilder(Integer.toBinaryString(one));
        StringBuilder bTwo = new StringBuilder(Integer.toBinaryString(two));
        int dOne = B - bOne.length();
        for (int i = 0; i < dOne; i++) {
            bOne.insert(0, "0");
        }
        int dTwo = B - bTwo.length();
        for (int i = 0; i < dTwo; i++) {
            bTwo.insert(0, "0");
        }
        //System.out.println(bOne);
        //System.out.println(bTwo);
        int edit = 0;
        for (int i = 0; i < bOne.length(); i++) {
            if (bOne.charAt(i) != bTwo.charAt(i)) {
                edit++;
            }
        }
        return edit >= dist;
    }
}