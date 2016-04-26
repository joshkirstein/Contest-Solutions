/*
ID: joshkir1
LANG: JAVA
TASK: stacking
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class stacking {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("stacking.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("stacking.out"));
        String[] inp = sc.readLine().split(" ");
        int stacks = Integer.parseInt(inp[0]);
        int commands = Integer.parseInt(inp[1]);
        int[] arr = new int[stacks];
        for (int i = 0; i < commands; i++) {
            String[] f = sc.readLine().split(" ");
            int low = Integer.parseInt(f[0]) - 1;
            int hi = Integer.parseInt(f[1]) - 1;
            for (int j = low; j <= hi; j++) {
                arr[j] += 1;
            }
        }
        Arrays.sort(arr);
        out.append(arr[(int) (arr.length / 2)] + "");
        out.newLine();
        out.close();
        //System.exit(0);
    }
}
