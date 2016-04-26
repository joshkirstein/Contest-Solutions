package Section24;


/*
ID: joshkir1
LANG: JAVA
TASK: fracdec
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class fracdec {
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("fracdec.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("fracdec.out"));
        //input processing output here
        String[] spl = sc.readLine().split(" ");
        int N = Integer.parseInt(spl[0]);
        int D = Integer.parseInt(spl[1]);
        class Frac {
            public int num, den;
            public Frac(int N, int D) {
                this.num = N;
                this.den = D;
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 97 * hash + this.num;
                hash = 97 * hash + this.den;
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Frac other = (Frac) obj;
                if (this.num != other.num) {
                    return false;
                }
                if (this.den != other.den) {
                    return false;
                }
                return true;
            }
            
        }
        //idea: reduce to a proper fraction
        //then generate each decimal digit until we find a cycle? :o
        int front = 0;//integer part
        if (N == D || N % D == 0) {
            out.append(N/D + ".0\n");
            out.close();
            return;
        } else if (N >= D) {
            int div = N / D;
            int num = div * D;
            front = div;
            N -= num;
        }
        String s = front + ".";
        HashMap<Frac, Integer> seen = new HashMap<Frac, Integer>();//first idx of occurence...
        StringBuilder br = new StringBuilder("");
        int curN = N * 10;
        int curD = D;
        Frac rep;
        for (int i = 0; ; i++) {
            //System.out.println(curN + " / " + curD);
            Frac f = new Frac(curN, curD);
            if (seen.containsKey(f)) {
                rep = f;
                break;
            }
            seen.put(f, i);
            int div = curN / curD;
            int num = div * curD;
            curN -= num;
            curN *= 10;
            br.append(div);
        }
        if (rep != null) {
            //make arrangements with br for parenthesis
            if (rep.num != 0) {
                br.append(")");
                br.insert(seen.get(rep), "(");
            } else {
                br.delete(br.length() - 1, br.length());//remove last char which is always 0
            }
        }
        br.insert(0, s);
        StringBuilder newBr = new StringBuilder("");
        boolean first = true;
        int printed = 0;
        for (int i = 0; i < br.length(); i++) {
            newBr.append(br.charAt(i));
            printed++;
            if (printed == 76) {
                newBr.append("\n");
                printed = 0;
            }
        }
        out.append(newBr);
        out.newLine();
        out.close();
    }
}