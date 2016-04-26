/*
ID: joshkir1
LANG: JAVA
TASK: runround
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class runround {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("runround.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("runround.out"));
        long M = Long.parseLong(sc.readLine());
        while (true) {
            if (isRunAround(++M)) {
                out.append(M + "\n");
                break;
            }
        }
        out.close();
    }
    
    public static boolean isRunAround(long l) {
        String s = Long.toString(l);
        boolean[] used = new boolean[10];
        int startIdx = 0;
        int amt = 0;
        while (true) {
            if (amt == s.length()) {
                if (startIdx != 0) return false;
                break;
            }
            int val = Integer.parseInt(s.charAt(startIdx) + "");
            if (used[val]) {
                return false;//reached this
            }
            used[val] = true;
            int nextIdx = (startIdx + val) % s.length();
            startIdx = nextIdx;
            amt++;
        }
        return true;
    }
}