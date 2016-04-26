import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FindTheCow {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("cowfind.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("cowfind.out"));
        char[] arr = sc.readLine().toCharArray();
        int num = 0;
        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == '(' && arr[i + 1] == '(') {
                //(( at i
                left.add(i);
            } else if (arr[i] == ')' && arr[i + 1] == ')') {
                //)) at i
                right.add(i);
            }
        }
        Collections.sort(left);
        Collections.sort(right, Collections.reverseOrder());
        ILOOP:
        for (int i = 0; i < left.size(); i++) {
            int leftIdx = left.get(i);
            for (int j = 0; j < right.size(); j++) {
                int rightIdx = right.get(j);
                if (rightIdx < leftIdx) {
                    //not a valid cow, and we can stop here because right goes from in decreasing order, means we're done with this leftIdx
                    continue ILOOP;
                } else {
                    num++;
                }
            }
        }
        out.append(num + "\n");
        out.close();
    }
}
