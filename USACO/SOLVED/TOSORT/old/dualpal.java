package old;

/*
ID: joshkir1
LANG: JAVA
TASK: dualpal
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class dualpal {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("dualpal.in")));
        PrintWriter out = new PrintWriter(new FileWriter("dualpal.out"));
        String[] spl = sc.nextLine().split(" ");
        int n = Integer.parseInt(spl[0]);
        int s = Integer.parseInt(spl[1]);
        while (n > 0) {
            if (check(++s)) {
                out.println(s);
                n--;
            }
        }
        out.close();
        System.exit(0);
    }
    
    public static boolean check(int i) {
        int count = 0;
        for (int n = 2; n <= 10; n++) {
            if (count == 2) return true;
            if (isPalindrome(Integer.toString(i, n))) {
                count++;
            }
        }
        return count == 2;
    }
    
    public static boolean isPalindrome(String s) {
        StringBuilder buf = new StringBuilder(s);
        if (buf.toString().equals(buf.reverse().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
