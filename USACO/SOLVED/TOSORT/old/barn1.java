package old;

/*
ID: joshkir1
LANG: JAVA
TASK: barn1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class barn1 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("barn1.in")));
        PrintWriter out = new PrintWriter(new FileWriter("barn1.out"));
        String[] info = sc.nextLine().split(" ");
        int m = Integer.parseInt(info[0]);
        int s = Integer.parseInt(info[1]);
        int c = Integer.parseInt(info[2]);
        ArrayList<Integer> occupied = new ArrayList<Integer>();
        for (int i = 0; i < c; i++) {
            occupied.add(Integer.parseInt(sc.nextLine()));
        }
        Collections.sort(occupied);
        ArrayList<Permutation> ps = new ArrayList<Permutation>();
        ps.add(new Permutation(occupied.get(0), occupied.get(occupied.size() - 1)));
        for(int i = 0; i < m - 1; i++) {
            int maxChange = -1;
            int maxCIdx = -1;
            Permutation zz = null;
            for (Permutation p : ps) {
                for (int n = 1; n < occupied.size(); n++) {
                    int low = occupied.get(n - 1);
                    int high = occupied.get(n);
                    if ((occupied.get(n) - occupied.get(n - 1)) > maxChange &&
                            (low >= p.from && low <= p.to) && (high >= p.from && high <= p.to)) {
                        maxCIdx = n;
                        maxChange = (occupied.get(n) - occupied.get(n - 1));
                        zz = p;
                    }
                }
            }
            if (zz != null) {
                ps.remove(zz);
                ps.add(new Permutation(zz.from, occupied.get(maxCIdx - 1)));
                ps.add(new Permutation(occupied.get(maxCIdx), zz.to));
            }
        }
        int count = 0;
        for (Permutation p : ps) {
            int max = Math.max(p.to, p.from);
            int min = Math.min(p.to, p.from);
            count += (max - min) + 1;
        }
        out.println(count);
        out.close();
        System.exit(0);
    }
    
    static class Permutation {
        public int from, to;
        public Permutation(int from, int to) {
            this.from = from;
            this.to = to;
        }
        public String toString() {
            return "(" + from + ", " + to + ")";
        }
        public boolean equals(Object o) {
            if (o instanceof Permutation) {
                Permutation p = (Permutation) o;
                if (p.from == from && p.to == to) return true;
            }
            return false;
        }
    }
}
