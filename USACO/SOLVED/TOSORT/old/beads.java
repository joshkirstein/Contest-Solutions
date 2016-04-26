package old;

/*
ID: joshkir1
LANG: JAVA
TASK: beads
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class beads {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("beads.in")));
        PrintWriter out = new PrintWriter(new FileWriter("beads.out"));
        int count = Integer.parseInt(sc.nextLine());
        String line = sc.nextLine();
        char[] beads = line.toCharArray();
        int max = 0;
        for (int i = 0; i < count; i++) {
            max = Math.max(max, count(beads, i));
        }
        //System.out.println(max);
        out.println(max);
        out.close();
        System.exit(0);
    }
    
    //s is inclusive
    public static int count(char[] arr, int s) {
        if (s != 0) {
            char[] newA = new char[arr.length];
            System.arraycopy(arr, s, newA, 0, arr.length - s);
            System.arraycopy(arr, 0, newA, arr.length - s, s);
            arr = newA;
        }
        boolean[] used = new boolean[arr.length];
        int count = 0;
        char cur = '\0';
        for (int i = 0; i < arr.length; i++) {
            if (cur == '\0') {
                if (arr[i] != 'w') {
                    cur = arr[i];
                }
                count++;
            } else {
                if (arr[i] == cur || arr[i] == 'w') {
                    count++;
                } else {
                    break;
                }
            }
            used[i] = true;
        }
        cur = '\0';
        for (int i = arr.length - 1; i >= 0; i--) {
            if (used[i]) break;
            if (cur == '\0') {
                if (arr[i] != 'w') {
                    cur = arr[i];
                }
                count++;
            } else {
                if (arr[i] == cur || arr[i] == 'w') {
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }
}
