package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: haybales
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class haybales {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("haybales.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("haybales.out"));
        int numBales = Integer.parseInt(sc.readLine());
        int tot = 0;
        int[] arr = new int[numBales];
        for (int i = 0; i < numBales; i++) {
            int read = Integer.parseInt(sc.readLine());
            tot += read;
            arr[i] = read;
        }
        int per = tot / numBales;
        int amtMove = 0;
        for (int i = 0; i < arr.length; i++) {
            int delta = per - arr[i];
            if (delta > 0)
                amtMove += delta;
        }
        out.append(amtMove + "\n");
        out.close();
        System.exit(0);
    }
    
    public static int[] findMinMax(int[] arr) {
        int minIdx = 0;
        int maxIdx = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[minIdx]) {
                minIdx = i;
            }
            if (arr[i] > arr[maxIdx]) {
                maxIdx = i;
            }
        }
        return new int[] { minIdx, maxIdx };
    }
}
