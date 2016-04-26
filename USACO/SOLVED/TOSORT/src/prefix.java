
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class prefix {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("prefix.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("prefix.out"));
        HashSet<String> alphabet = new HashSet<String>();
        String cur = "";
        while (!(cur = sc.readLine()).equals(".")) {
            String[] spl = cur.split(" ");
            for (int i = 0; i < spl.length; i++) {
                alphabet.add(spl[i]);
            }
        }
        StringBuilder seq = new StringBuilder("");
        while ((cur = sc.readLine()) != null) {
            seq.append(cur);
        }
        String str = seq.toString();
        int[] L = new int[seq.length()];
        int endIdx = 0;//exclusive
        for (int i = 0; i < L.length; i++) {
            String sub = str.substring(endIdx, i + 1);//including i, so i + 1
            if (alphabet.contains(sub)) {
                int len = sub.length();
            } else {
                if (i != 0)
                    L[i] = L[i - 1];
            }
        }
        out.close();
    }
}