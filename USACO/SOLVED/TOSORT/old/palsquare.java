package old;

/*
ID: joshkir1
LANG: JAVA
TASK: palsquare
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class palsquare {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("palsquare.in")));
        PrintWriter out = new PrintWriter(new FileWriter("palsquare.out"));
        int radix = Integer.parseInt(sc.nextLine());
        for (int i = 1; i <= 300; i++) {
            if (isPalindrome(Integer.toString((int) Math.pow(i, 2), radix))) {
                out.println(Integer.toString(i, radix).toUpperCase() + " " + Integer.toString((int) Math.pow(i, 2), radix).toUpperCase());
            }
        }
        out.close();
        System.exit(0);
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
