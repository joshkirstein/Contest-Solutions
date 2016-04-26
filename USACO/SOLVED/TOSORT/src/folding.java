/*
ID: joshkir1
LANG: JAVA
TASK: folding
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class folding {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("folding.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("folding.out"));
        String[] split = sc.readLine().split(" ");
        int N  = Integer.parseInt(split[0]);
        int L = Integer.parseInt(split[1]);
        int[] knots = new int[N];
        for (int i = 0; i < N; i++) {
            knots[i] = Integer.parseInt(sc.readLine());
        }
        Arrays.sort(knots);
        int count = 0;
        MAAIN:
        for (int i = 1; i < L; i++) {//non endpoints all points...
            int leftIDX = findKnot(i, true, knots);
            int rightIDX = findKnot(i, false, knots);
            if (leftIDX == Integer.MIN_VALUE || rightIDX == Integer.MAX_VALUE) continue MAAIN;
            while (true) {
                if (leftIDX == Integer.MIN_VALUE || rightIDX == Integer.MAX_VALUE) {
                    count++;
                    continue MAAIN;
                }
                int difL = Math.abs(i - leftIDX);
                int difR = Math.abs(i - rightIDX);
                if (difL != difR) {
                    continue MAAIN;
                } else {
                    leftIDX = findKnot(leftIDX, true, knots);
                    rightIDX = findKnot(rightIDX, false, knots);
                }
            }
        }
        out.append(count + "");
        out.newLine();
        out.close();
    }
    
    public static int findKnot(int idx, boolean left, int[] knots) {
        if (left) {
            int lastIDX = Integer.MIN_VALUE;
            for (int i = 0; i < knots.length; i++) {
                if (knots[i] >= idx) break;
                if (knots[i] > lastIDX) {
                    lastIDX = knots[i];
                } else {
                    break;
                }
            }
            return lastIDX;
        } else {
            int lastIDX = Integer.MAX_VALUE;//end val
            for (int i = knots.length - 1; i >= 0; i--) {
                if (knots[i] <= idx) break;
                if (knots[i] < lastIDX) {
                    lastIDX = knots[i];
                } else {
                    break;
                }
            }
            return lastIDX;
        }
    }
}
