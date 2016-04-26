/*
ID: joshkir1
LANG: JAVA
TASK: preface
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class preface {
    
    static int[] vals = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
    static int[] letVal = { 1, 5, 10, 50, 100, 500, 1000 };
    static char[] letters = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };
    static String[] mapping = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("preface.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("preface.out"));
        int input = Integer.parseInt(sc.readLine());
        TreeMap<Character, Integer> sup = new TreeMap<Character, Integer>();
        char highest = '\u0000';
        int max = -1;
        int maxIdx = -1;
        for (int inpLo = 1; inpLo <= input; inpLo++) {
            //System.out.println("sup");
            int inp = inpLo;
            for (int i = vals.length - 1; i >= 0; i--) {
                int VAL = vals[i];
                if (VAL > inp) continue;
                int div = inp / VAL;
                String map = mapping[i];
                for (Character c : map.toCharArray()) {
                    if (div == 0) continue;
                    int idx = charIdx(c);
                    if (letVal[idx] > max) {
                        highest = c;
                        max = letVal[idx];
                        maxIdx = idx;
                    }
                    if (!sup.containsKey(c)) {
                        sup.put(c, div);
                    } else {
                        sup.put(c, sup.get(c) + div);
                    }
                }
                inp -= VAL * div;
            }
        }
        for (int i = 0; i <= maxIdx; i++) {
            out.append(letters[i] + " " + sup.get(letters[i]) + "\n");
        }
        out.close();
    }
    
    public static int charIdx(char c) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == c) {
                return i;
            }
        }
        return -1;
    }
}