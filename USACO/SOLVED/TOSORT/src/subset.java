/*
ID: joshkir1
LANG: JAVA
TASK: subset
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class subset {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("subset.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("subset.out"));
        int N = Integer.parseInt(sc.readLine());
        int SUM = (N * (1 + N)) / 2;
        if (SUM % 2 != 0) {
            //Cannot split it
            out.append("0\n");
            out.close();
            return;
        }
        long[] sum = new long[SUM + 1];
        sum[0] = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = SUM - i; j >= 0; j--) {//back to front to avoid using the same number in the sum
                sum[i + j] += sum[j];
            }
        }
        System.out.println(sum[SUM / 2] / 2);
        out.append((sum[SUM / 2] / 2) + "\n");
        out.close();
    }
}