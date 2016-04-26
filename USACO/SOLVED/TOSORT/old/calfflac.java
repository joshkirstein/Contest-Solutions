package old;

/*
ID: joshkir1
LANG: JAVA
TASK: calfflac
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class calfflac {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader(new File("calfflac.in")));
        PrintWriter out = new PrintWriter(new FileWriter("calfflac.out"));
        String line = sc.readLine() + "\n";
        String temp = "";
        while ((temp = sc.readLine()) != null) {
            String next = temp + "\n";
            if (next.equals("")) {
                break;
            }
            line += next;
        }
        char[] arr = line.toCharArray();
        int[] data = getLongestPalindrome(arr);
        StringBuilder b = new StringBuilder("");
        int maxLength = 0;
        for (int i = data[0]; i <= data[1]; i++) {
            b.append(arr[i]);
            if (Character.isLetter(arr[i])) maxLength++;
        }
        out.println(maxLength);
        out.println(b.toString());
        out.close();
        System.exit(0);
    }

    public static int[] getLongestPalindrome(char[] arr) {
        int[] hold = null;
        int longest = 0;
        for (double i = 1; i < arr.length; i++) {
            boolean middle = false;
            if (arr[(int) i] == arr[(int) i - 1]) {//middle shiit
                i -= 0.5D;
                middle = true;
            }
            int[] temp = getLongestPalindrome(arr, i);
            int delta = 0;
            if (temp.length == 2) {
                delta = temp[1];
            } else {
                delta = temp[2];
            }
            if (delta > longest) {
                hold = temp;
                longest = delta;
                if (delta >= 2000) {
                    return temp;
                }
            }
            if (middle) {
                i += 0.5D;//restore order :)
            }
        }
        return hold;
    }
    
    private static int[] getLongestPalindrome(char[] arr, double start) {
        int left = (int) start - 1;
        int right = (int) start + 1;
        if (left < 0 || right >= arr.length)  {
            return new int[] { (int) start, 0 };
        }
        int size = start % 1 == 0.5D ? 0 : 1;
        boolean init = true;
        while (left >= 0 && right < arr.length) {
            int tLeft = left;
            int tRight = right;
            if (init) {
                init = false;
            } else {
                tLeft--;
                tRight++;
            }
            while (tLeft > 0 && !Character.isLetter(arr[tLeft])) {
                tLeft--;
            }
            while (tRight < arr.length - 1 && !Character.isLetter(arr[tRight])) {
                tRight++;
            }
            if (!init) {
                //if (tLeft == left || tRight == right) //break;
            }
            if (tLeft < 0 || tRight > arr.length) break;
            if (Character.toLowerCase(arr[tLeft]) == Character.toLowerCase(arr[tRight])) {
                left = tLeft;
                right = tRight;
                size += 2;
                continue;
            } else {
                break;
            }
        }
        return new int[] {left, right, size};
    }
}
