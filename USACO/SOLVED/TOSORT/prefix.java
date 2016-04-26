
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

public class prefix {
    /*USER: Joshua Kirstein [joshkir1]
TASK: prefix
LANG: JAVA

Compiling...
Compile: OK

Executing...
   Test 1: TEST OK [0.115 secs, 274832 KB]
   Test 2: TEST OK [0.108 secs, 274832 KB]
   Test 3: TEST OK [0.173 secs, 275856 KB]
   Test 4: TEST OK [0.187 secs, 275856 KB]
   Test 5: TEST OK [0.194 secs, 275856 KB]
   Test 6: TEST OK [0.626 secs, 276880 KB]

All tests OK.
Your program ('prefix') produced all correct answers!  This is your
submission #15 for this problem.  Congratulations!*/
    static String[] prefixes = null;
    static String seq = null;
    static int[] memo;
    
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
        memo = new int[seq.length()];
        Arrays.fill(memo, -1);
        max = DP(0);
        System.out.println(max);
        out.append(max + "\n");
        System.out.println("Took: " + (System.currentTimeMillis() - ms) + " ms to run");
        out.close();
    }
    
    public static int DP(int i) {//DP(i) = longest prefix length starting at i
        if (i >= seq.length()) return 0;
        if (memo[i] != -1) return memo[i];
        int max = 0;
        LOOP:
        for (String s : prefixes) {
            int e = i + s.length();
            if (e > seq.length()) continue;//not possible
            for (int j = i; j < e; j++) {
                if (seq.charAt(j) != s.charAt(j - i)) {
                    continue LOOP;
                }
            }
            max = Math.max(max, s.length() + DP(e));
        }
        return memo[i] = max;
    }
}