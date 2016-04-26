/*
ID: joshkir1
LANG: JAVA
TASK: times17
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class times17 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("times17.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("times17.out"));
        String s = sc.readLine();
        BigInteger bi = new BigInteger(s, 2);
        bi = bi.multiply(new BigInteger("17"));
        out.append(bi.toString(2));
        out.newLine();
        out.close();
    }
}
