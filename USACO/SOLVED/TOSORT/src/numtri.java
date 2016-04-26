/*
ID: joshkir1
LANG: JAVA
TASK: numtri
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class numtri {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("numtri.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("numtri.out"));
        int amt = Integer.parseInt(sc.readLine());
        int[][] arr = new int[amt][amt];
        int[][] MAX = new int[amt][amt];
        for (int i = 0; i < amt; i++) {
            String[] spl = sc.readLine().split(" ");
            for (int j = 0; j < spl.length; j++) {
                arr[i][j] = Integer.parseInt(spl[j]);
                if (i == amt - 1) {
                    MAX[i][j] = Integer.parseInt(spl[j]);
                }
            }
        }
        //System.out.println(Arrays.toString(MAX[amt - 1]));
        for (int i = amt - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                MAX[i][j] = arr[i][j] + Math.max(MAX[i + 1][j], MAX[i + 1][j + 1]);
            }
            //System.out.println(Arrays.toString(MAX[i]));
        }
        out.append(MAX[0][0] + "\n");
        out.close();
    }
}