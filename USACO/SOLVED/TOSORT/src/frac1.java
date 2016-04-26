/*
ID: joshkir1
LANG: JAVA
TASK: frac1
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class frac1 {
    
    static class Frac implements Comparable<Frac> {
        public int num, denom;
        public Frac(int num, int denom) {
            int x;
            while ((x = gcd(num, denom)) != 1) {
                num /= x;
                denom /= x;
            }
            this.num = num;
            this.denom = denom;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + this.num;
            hash = 71 * hash + this.denom;
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
            if (this.denom != other.denom) {
                return false;
            }
            return true;
        }
        
        public int compareTo(Frac o) {
            double dub = (double) num / (double) denom;
            double dub2 = (double) o.num / (double) o.denom;
            if (dub < dub2) {
                return 1;
            } else if (dub > dub2) {
                return -1;
            } else {
                return 0;
            }
        }
        public String toString() {
            return num + "/" + denom;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("frac1.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("frac1.out"));
        int N = Integer.parseInt(sc.readLine());
        TreeSet<Frac> set = new TreeSet<Frac>();
        out.append("0/1\n");
        for (int i = 1; i <= N; i++) {
            for (int j = i; j <= N; j++) {
                set.add(new Frac(i, j));
            }
        }
        for (Frac f : set.descendingSet()) {
            out.append(f + "\n");
        }
        out.close();
    }
    
    public static int gcd(int x, int y) {
        if (y == 0) return x;
        return gcd(y, x%y);
    }
}