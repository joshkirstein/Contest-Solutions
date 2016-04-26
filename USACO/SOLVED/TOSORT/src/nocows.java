/*
ID: joshkir1
LANG: JAVA
TASK: nocows
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class nocows {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("nocows.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("nocows.out"));
        String[] spl = sc.readLine().split(" ");
        int N = Integer.parseInt(spl[0]);
        int K = Integer.parseInt(spl[1]);
        //input processing output here
        //A[N, K] = amount of TREES with N vertices and depth K
        //L[N, K] = number of LEAVES with N vertices and depth K
        //L[N, K] = L[XXX, K-1]
        //First find K where 
        int[][] A = new int[N + 1][K + 1];
        int[][] L = new int[N + 1][K + 1];
        A[1][1] = 1;//Only 1 tree like this
        L[1][1] = 1;//Only 1 leaf.
        
        out.close();
    }
}