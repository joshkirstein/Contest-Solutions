package old;

/*
ID: joshkir1
LANG: JAVA
TASK: namenum
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class namenum {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("namenum.in")));
        PrintWriter out = new PrintWriter(new FileWriter("namenum.out"));
        Scanner dict = new Scanner(new FileReader(new File("dict.txt")));
        HashSet<String> set = new HashSet<String>();
        while (dict.hasNextLine()) {
            set.add(dict.nextLine());
        }
        complete(sc.nextLine(), "", set);
        Collections.sort(possibles);
        for (String n : possibles) {
            out.println(n);
        }
        if (possibles.size() == 0) {
            out.println("NONE");
        }
        out.close();
        System.exit(0);
    }
    
    static ArrayList<String> possibles = new ArrayList<String>();
    
    public static void complete(String serial, String str, HashSet<String> valid) {
        if (serial.length() <= 0) {
            if (valid.contains(str)) {
                possibles.add(str);
            }
        } else {
            for (String next : possible(serial.charAt(0))) {
                complete(serial.substring(1, serial.length()), str + next, valid);
            }
        }
    }
    
    public static String[] possible(char s) {
        if (s == '2') {
            return new String[] { "A", "B", "C" };
        } else if (s == '3') {
            return new String[] { "D", "E", "F" };
        } else if (s == '4') {
            return new String[] { "G", "H", "I" };
        } else if (s == '5') {
            return new String[] { "J", "K", "L" };
        } else if (s == '6') {
            return new String[] { "M", "N", "O" };
        } else if (s == '7') {
            return new String[] { "P", "R", "S" };
        } else if (s == '8') {
            return new String[] { "T", "U", "V" };
        } else if (s == '9') {
            return new String[] { "W", "X", "Y" };
        } else {
            return new String[0];
        }
    }
}