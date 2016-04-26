package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: numgrid
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class numgrid {
    
    static String[][] grid = new String[5][5];
    static boolean[] used = new boolean[1000000];
    static int count = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("numgrid.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("numgrid.out"));
        for (int i = 0; i < 5; i++) {
            String[] line = sc.readLine().split(" ");
            for (int j = 0; j < 5; j++) {
                grid[i][j] = line[j];
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j  = 0; j < 5; j++) {
                runAt(i, j, "");
            }
        }
        System.out.println(count);
        out.append(count + "\n");
        out.close();
        System.exit(0);
    }
    
    static int[][] arr =
    {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 }
    };
    
    public static void runAt(int x, int y, String cur) {
        if (cur.length() == 6) {
            int val = Integer.parseInt(cur);
            if (!used[val]) {
                used[val] = true;
                count++;
            }
        } else {
            String s = cur + grid[x][y];
            for (int[] a : arr) {
                int deltX = x + a[0];
                int deltY = y + a[1];
                try {
                   runAt(deltX, deltY, s);
                } catch (Exception ex) {
                }//aiobe
            }
        }
    } 
}
