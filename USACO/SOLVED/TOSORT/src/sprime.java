/*
ID: joshkir1
LANG: JAVA
TASK: sprime
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class sprime {
    
    static int[] digs = { 1, 3, 5, 7, 9 };
    static int N;
    static BufferedWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("sprime.in"));
        out = new BufferedWriter(new FileWriter("sprime.out"));
        //input processing output here
        N = Integer.parseInt(sc.readLine());
        MAIN:
        generatePossible((int) Math.pow(10, N-1), 0, true);
        out.close();
    }
    
    public static void generatePossible(int curPow, int curNum, boolean firs) throws IOException {
        if (curPow < 1) {
            //out.append(curNum + "\n");
            if (isSuper(curNum)) {
                out.append(curNum + "\n");
            }
        } else {
            int[] digs = sprime.digs;
            if (firs) {
                firs = false;
                digs = new int[] { 2, 3, 5, 7 };
            }
            for (int i = 0; i < digs.length; i++) {
                int digit = digs[i];
                //use digit
                generatePossible(curPow / 10, curNum + curPow * digit, firs);
            }
        }
    }
    
    public static boolean isSuper(int num) {
        for (int amt = 1; amt <= N; amt++) {
            int delt = N - amt;
            int pow = (int) Math.pow(10, delt);
            int resid = (int) ((double) num / (double) pow);
            //System.out.println(resid);
            if (!isPrime(resid)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isPrime(int num) {
        if (num == 0 || num == 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num) + 1; i += 2) {//only need to check up to sqrt(num) ;)
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}