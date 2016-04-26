package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: photo
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class photo {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("photo.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("photo.out"));
        int numCows = Integer.parseInt(sc.readLine().trim());
        int[][] arrs = new int[numCows][numCows + 1];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < numCows; j++) {
                int ID = Integer.parseInt(sc.readLine().trim());
                int minus = getWrap(arrs, j - 1);
                int cur = j;
                int after = getWrap(arrs, j + 1);
                arrs[minus][ID]++;
                arrs[cur][ID]++;
                arrs[after][ID]++;
            }
        }
        HashSet<Integer> used = new HashSet<Integer>();
        for (int i = 0; i < arrs.length; i++) {
            int[] h = arrs[i];
            int maxID = 0;
            int maxCount = 0;
            for (int j = 0; j < h.length; j++) {
                int id = j;
                int count = h[j];
                if (used.contains(id)) {
                    continue;
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxID = id;
                }
            }
            out.append(maxID + "");
            if (i != (arrs.length - 1)) {
                out.newLine();
            }
            used.add(maxID);
        }
        out.close();
        System.exit(0);
    }
    
    public static int getWrap(Object[] arr, int num) {
        if (num < 0) {
            return arr.length - 1;
        } else if (num >= arr.length) {
            return 0;
        } else {
            return num;
        }
    }
}
