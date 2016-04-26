/*
ID: joshkir1
LANG: JAVA
TASK: overplanting
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class overplanting {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("planting.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("planting.out"));
        int N = Integer.parseInt(sc.readLine());
        int space = 0;
        boolean[][] seen = new boolean[20001][20001];
        for (int i = 0; i < N; i++) {
            String[] spl = sc.readLine().split(" ");
            int x1 = Integer.parseInt(spl[0]) + 10000;
            int y1 = Integer.parseInt(spl[1]) + 10000;
            int x2 = Integer.parseInt(spl[2]) + 10000;
            int y2 = Integer.parseInt(spl[3]) + 10000;
            for (int j = x1; j >= x2; j--) {
                for (int k = y1; k >= y2; k--) {
                    System.out.println("ss");
                    if (!seen[k][j]) {
                        System.out.println("fafsd");
                        seen[k][j] = true;
                        space++;
                    }
                }
            }
        }
        out.append(space + "");
        out.newLine();
        out.close();
    }
}
