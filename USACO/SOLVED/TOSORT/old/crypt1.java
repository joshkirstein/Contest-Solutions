package old;

/*
ID: joshkir1
LANG: JAVA
TASK: crypt1
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class crypt1 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader(new File("crypt1.in")));
        PrintWriter out = new PrintWriter(new FileWriter("crypt1.out"));
        sc.readLine();//we dont care about digits
        String[] line = sc.readLine().split(" ");
        int[] digits = new int[line.length];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = Integer.valueOf(line[i]);
        }
        run(new int[5], 0, digits);
        out.println(hashSet.size());
        out.close();
    }
    
    static HashSet<Data> hashSet = new HashSet<Data>();
    
    static class Data {
        
        public int top, bot, part1, part2, res;
        
        public Data(int top, int bot, int part1, int part2, int res) {
            this.top = top;
            this.bot = bot;
            this.part1 = part1;
            this.part2 = part2;
            this.res = res;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Data other = (Data) obj;
            if (this.top != other.top) {
                return false;
            }
            if (this.bot != other.bot) {
                return false;
            }
            if (this.part1 != other.part1) {
                return false;
            }
            if (this.part2 != other.part2) {
                return false;
            }
            if (this.res != other.res) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.top;
            hash = 97 * hash + this.bot;
            hash = 97 * hash + this.part1;
            hash = 97 * hash + this.part2;
            hash = 97 * hash + this.res;
            return hash;
        }
    }
    
    public static void run(int[] arr, int curPos, final int[] DIGITS) {
        if (curPos >= arr.length) {
            //do stuff
            int top = Integer.parseInt("" + arr[0] + arr[1] + arr[2]);
            int bot =  Integer.parseInt("" + arr[3] + arr[4]);
            int part1 = part1(arr, top);
            int part2 = part2(arr, top);
            int res = top * bot;
            if (containsOnly(part1, DIGITS, true) && containsOnly(part2, DIGITS, true) && containsOnly(res, DIGITS, false)) {
                Data d = new Data(top, bot, part1, part2, res);
                hashSet.add(d);
            }
            return;
        }
        for (int i = 0; i < DIGITS.length; i++) {
            //if (DIGITS[i] == 2 || DIGITS[i] == 5 || DIGITS[i] == 7 || DIGITS[i] == 3) {
                arr[curPos] = DIGITS[i];
                run(arr, curPos + 1, DIGITS);
            //}
        }
    }
    
    public static boolean containsOnly(int tst, int[] DIGITS, boolean isPart) {
        String s = String.valueOf(tst);
        if (isPart) {
            if (s.length() != 3) return false;
        } else {
            if (s.length() != 4) return false;
        }
        for (int i = 0; i < s.length(); i++) {
            int dig = Integer.parseInt("" + s.charAt(i));
            boolean has = false;
            for (int z = 0; z < DIGITS.length; z++) {
                if (DIGITS[z] == dig) {
                    has = true;
                }
            }
            if (!has) return false;
        }
        return true;
    }
    
    public static int part1(int[] arr, int top) {
        return arr[4] * top;
    }
    
    public static int part2(int[] arr, int top) {
        return arr[3] * top;
    }
}
