package old.newn;



/*
ID: joshkir1
LANG: JAVA
TASK: passwd
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class passwd {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("passwd.in")));
        PrintWriter out = new PrintWriter(new FileWriter("passwd.out"));
        String[] info = sc.nextLine().split(" ");
        int length = Integer.parseInt(info[0]);
        int amtC = Integer.parseInt(info[1]);
        String[] chars = sc.nextLine().split(" ");
        //char max = 'z';
        /*for (String s : chars) {
            if (s.equals("a")
                    || s.equals("e")
                    || s.equals("i")
                    || s.equals("o")
                    || s.equals("u")) {
                if (s.charAt(0) > max) {
                    max = s.charAt(0);
                }
            }
        }*/
        Arrays.sort(chars);
        run(out, chars, length, "", 0);
        out.close();
        System.exit(0);
    }
    
    static int count = 0;
    
    public static void run(PrintWriter out, String[] chars, int desiredLength, String cur, int vowels/*, char maxVowel*/) {
        if (count >= 25000) return;
        if (cur.length() == desiredLength) {
            if (vowels > 0) {
                int cons = cur.length() - vowels;
                if (cons > 1) {
                    out.println(cur);
                    count++;
                }
            }
        } else {
            if (cur.length() == 0) {
                for (String s : chars) {
                    int vowelC = 0;
                    if (s.equals("a") ||
                            s.equals("e") ||
                            s.equals("i") ||
                            s.equals("o") ||
                            s.equals("u")) {
                        vowelC = 1;
                    }
                    run(out, chars, desiredLength, "" + s, vowelC/*, maxVowel*/);
                }
            } else {
                char last = cur.charAt(cur.length() - 1);
                //if (last > maxVowel && vowels <= 0) return;
                for (String s : chars) {
                    if (last < s.charAt(0)) {
                        int vowelC = 0;
                        if (s.equals("a")
                                || s.equals("e")
                                || s.equals("i")
                                || s.equals("o")
                                || s.equals("u")) {
                            vowelC = 1;
                        }
                        run(out, chars, desiredLength, cur + s, vowels + vowelC/*, maxVowel*/);
                    }
                }
            }
        }
    }
}
