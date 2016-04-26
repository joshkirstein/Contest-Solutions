package delos;

/*
ID: joshkir1
LANG: JAVA
TASK: ariprog
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ariprog {
    
    static boolean[] bisquare;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("ariprog.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("ariprog.out"));
        int N_MAX = Integer.parseInt(sc.readLine());
        int M = Integer.parseInt(sc.readLine());
        bisquare = new boolean[M * M + M * M + 1];//bisquare[p^2+q^2]=true for all p and q non-neg
        for (int p = 0; p <= M; p++) {
            for (int q = 0; q <= M; q++) {
                bisquare[p * p + q * q] = true;
            }
        }
        boolean test = false;
        int MAX = M * M + M * M;
        BLOOP:
        for (int b = 1; b <= MAX; b++) {
            LOOP:
            for (int a = 0; a < bisquare.length; a++) {
                if (a + (N_MAX - 1) * b >= bisquare.length) continue BLOOP;//never possible
                if (!bisquare[a]) continue;
                for (int i = 1; i < N_MAX; i++) {
                    if (a + i * b >= bisquare.length) {
                        continue LOOP;//not valid
                    }
                    if (!bisquare[a + i * b]) {
                        if (a == 13 && b == 12) {
                            //System.out.println(a + i * b);
                            //System.out.println(i);
                            //System.out.println(bisquare[a + i * b]);
                        }
                        continue LOOP;
                    }
                }
                //VALID BISQURARE, PRINT
                out.append(a + " " + b + "\n");
                test = true;
            }
        }
        if (!test) {
            out.append("NONE\n");
        }
        out.close();
    }
}