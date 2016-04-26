
/*
ID: joshkir1
LANG: JAVA
TASK: prefix
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class prefix2 {//Bottom-up version FASTER...:o   
    /*
     * USER: Joshua Kirstein [joshkir1]
TASK: prefix
LANG: JAVA

Compiling...
Compile: OK

Executing...
   Test 1: TEST OK [0.079 secs, 274832 KB]
   Test 2: TEST OK [0.122 secs, 274832 KB]
   Test 3: TEST OK [0.173 secs, 274832 KB]
   Test 4: TEST OK [0.173 secs, 274832 KB]
   Test 5: TEST OK [0.158 secs, 274832 KB]
   Test 6: TEST OK [0.389 secs, 275856 KB]

All tests OK.
Your program ('prefix') produced all correct answers!  This is your
submission #14 for this problem.  Congratulations!
     */
    
    static String[] prefixes = null;
    static String seq = null;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("prefix.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("prefix.out"));
        //input processing output here
        StringBuilder brr = new StringBuilder("");
        String inp = null;
        boolean first = true;
        while (!(inp = sc.readLine()).equals(".")) {
            if (first) {
                brr.append(inp);
                first = false;
            } else {
                brr.append(" " + inp);
            }
        }
        System.out.println(brr);
        prefixes = brr.toString().split(" ");
        StringBuilder br = new StringBuilder("");
        inp = null;
        while ((inp = sc.readLine()) != null) {
            br.append(inp);
        }
        seq = br.toString();
        System.out.println(seq);
        long ms = System.currentTimeMillis();
        int max = 0;
        //DP HERE:
        int[] DP = new int[seq.length() + 1];//DP[i] = longest prefix sequence ENDING in i...
        for (int i = 0; i < seq.length(); i++) {
            if (i != 0 && DP[i] == 0) continue;
            LOOP:
            for (String s : prefixes) {
                int e = i + s.length();
                if (e > seq.length()) continue;//can't...
                for (int j = i; j < e; j++) {
                    if (seq.charAt(j) != s.charAt(j - i)) continue LOOP;
                }
                DP[e] = Math.max(DP[e], s.length() + DP[i]);
                max = Math.max(max, DP[e]);
            }
           // if (DP[i] == 0) break;
        }
        System.out.println(max);
        out.append(max + "\n");
        System.out.println("Took: " + (System.currentTimeMillis() - ms) + " ms to run");
        out.close();
    }
}