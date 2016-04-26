/*
ID: joshkir1
LANG: JAVA
TASK: moo
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class moo {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("moo.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("moo.out"));
        int N = Integer.parseInt(sc.readLine());
        StringBuilder last = new StringBuilder("moo");//S(0)
        int cur = 1;
        while (last.length() < N) {
            //System.out.println(last);
            StringBuilder middle = new StringBuilder("m");
            for (int i = 0; i < cur + 2; i++) {
                middle.append("o");
            }
            cur++;
            last.append(middle.append(last));
        }
        //System.out.println(cur);
        //System.out.println(last);
        //System.out.println(last.charAt(N - 1));//0 based
        out.append(last.charAt(N - 1));
        out.newLine();
        out.close();
    }
}
