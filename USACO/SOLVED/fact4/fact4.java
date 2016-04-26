
/*
ID: joshkir1
LANG: JAVA
TASK: fact4
*/
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class fact4 {
    
    public static boolean DEBUG = false;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("fact4.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("fact4.out"));
        int N = sc.nextInt();
        int dig = 1 % 10;//1
        int mtwos = 0;
        int mfives = 0;
        for (int i = 2; i <= N; i++) {
            int temp = i;
            while (temp % 5 == 0) {
                mfives++;
                temp /= 5;
            }
            while (temp % 2 == 0) {
                mtwos++;
                temp /= 2;
            }
            dig = (dig*temp) % 10;
            //dig = (dig*temp)%10 prevents overflow...dont use dig *= temp % 10 (useless prettymuch)
            //also note, you could just %10 as much as you'd like to prevent overflow (initially what i did)
            if (DEBUG) {
                System.out.println("AFTER i: " + i);
                System.out.println("CURDIG: " + dig);
            }
        }
        if (DEBUG) {
            System.out.println("MTWOS: " + mtwos);
            System.out.println("MFIVES: " + mfives);
        }
        if (mtwos > 0 && mfives > 0) {
            if (mtwos < mfives) {
                int sub = mfives - mtwos;
                for (int i = 0; i < sub; i++) {
                    dig = (dig*5) % 10;
                }
            } else if (mtwos > mfives) {
                int sub = mtwos - mfives;
                for (int i = 0; i < sub; i++) {
                    dig = (dig*2) % 10;
                }
            }
        } else if (mtwos > 0) {
            for (int i = 0; i < mtwos; i++) {
                dig = (dig*2) % 10;
            }
        } else if (mfives > 0) {
            for (int i = 0; i < mfives; i++) {
                dig = (dig*5) % 10;
            }
        }
        if (DEBUG)
            System.out.print(dig%10+ "\n");
        out.append(dig%10+ "\n");
        out.close();
    }
}