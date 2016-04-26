package old;

/*
ID: joshkir1
LANG: JAVA
TASK: milk2
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class milk2 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("milk2.in")));
        PrintWriter out = new PrintWriter(new FileWriter("milk2.out"));
        int N = Integer.parseInt(sc.nextLine());
        boolean[] b = new boolean[1000001];
        int min = -1;
        int max = -1;
        for (int i = 0; i < N; i++) {
            String[] spl = sc.nextLine().split(" ");
            int s = Integer.parseInt(spl[0]);
            int e = Integer.parseInt(spl[1]);
            if (min == -1) {
                min = s;
            } else {
                min = Math.min(min, s);
            }
            if (max == -1) {
                max = e;
            } else {
                max = Math.max(max, e);
            }
            for (int j = s; j < e; j++) {
                b[j] = true;
            }
        }
        int maxTrue = 0;
        int curCount = 0;
        for (int i = min; i <= max; i++) {
            if (b[i]) {
                curCount++;
            } else {
                maxTrue = Math.max(maxTrue, curCount);
                curCount = 0;
            }
        }
        int maxFalse = 0;
        curCount = 0;
        for (int i = min; i <= max; i++) {
            if (!b[i]) {
                curCount++;
            } else {
                maxFalse = Math.max(maxFalse, curCount);
                curCount = 0;
            }
        }
        out.println(maxTrue + " " + maxFalse);
        out.close();
        System.exit(0);
    }
}
