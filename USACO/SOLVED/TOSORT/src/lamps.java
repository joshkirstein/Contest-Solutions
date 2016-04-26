/*
ID: joshkir1
LANG: JAVA
TASK: lamps
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;

public class lamps {
    
    static int N;
    static int C;
    
    //button operations can be in any order: commutative
    //the same button operation cancels itself out if used in even numbers
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("lamps.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("lamps.out"));
        N = Integer.parseInt(sc.readLine());
        C = Integer.parseInt(sc.readLine());
        int[] arr = new int[N];
        Arrays.fill(arr, -1);
        String[] spl = sc.readLine().split(" ");
        for (int i = 0; i < spl.length; i++) {
            int j = Integer.parseInt(spl[i]);
            if (j == -1) break;
            arr[j - 1] = 1;
        }
        spl = sc.readLine().split(" ");
        for (int i = 0; i < spl.length; i++) {
            int j = Integer.parseInt(spl[i]);
            if (j == -1) break;
            arr[j - 1] = 0;
        }
        BitSet initial = new BitSet(N);
        for (int i = 1; i <= N; i++) {
            initial.set(i - 1, true);
        }
        HashSet<BitSet> set = new HashSet<BitSet>();
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                for (int k = 0; k <= 1; k++) {
                    for (int l = 0; l <= 1; l++) {
                        BitSet cur = (BitSet) initial.clone();
                        if (i == 1) {
                            cur = buttonOne(cur);
                        }
                        if (j == 1) {
                            cur = buttonTwo(cur);
                        }
                        if (k == 1) {
                            cur = buttonThree(cur);
                        }
                        if (l == 1) {
                            cur = buttonFour(cur);
                        }
                        int sum = i + j + k + l;
                        if (sum > C) {
                            continue;
                        }
                        boolean worked = true;
                        for (int z = 0; z < arr.length; z++) {
                            if (arr[z] != -1) {
                                int val = arr[z];
                                boolean bool = false;
                                if (val == 1) bool = true;
                                if (cur.get(z) != bool) {
                                    worked = false;
                                    break;
                                }
                            }
                        }
                        if (worked) {
                            set.add(cur);
                        }
                    }
                }
            }
        }
        class Output implements Comparable<Output> {
            public String str;
            public BigInteger integer;
            public Output(String str, BigInteger integer) {
                this.str = str;
                this.integer = integer;
            }
            public int compareTo(Output o) {
                return integer.compareTo(o.integer);
                //return integer - o.integer;
            }
        }
        ArrayList<Output> output = new ArrayList<Output>();
        for (BitSet b : set) {
            StringBuilder str = new StringBuilder("");
            for (int i = 0; i < N; i++) {
                if (b.get(i)) {
                    str.append("1");
                } else {
                    str.append("0");
                }
            }
            output.add(new Output(str.toString(), new BigInteger(str.toString(), 2)));
        }
        Collections.sort(output);
        for (Output o : output) {
            out.append(o.str + "\n");
        }
        if (output.size() == 0) {
            out.append("IMPOSSIBLE\n");
        }
        out.close();
    }
    
    public static BitSet buttonOne(BitSet cur) {
        BitSet nex = (BitSet) cur.clone();
        for (int i = 1; i <= N; i++) {
            boolean curVal = cur.get(i - 1);
            nex.set(i - 1, !curVal);
        }
        return nex;
    }
    
    public static BitSet buttonTwo(BitSet cur) {
        BitSet nex = (BitSet) cur.clone();
        for (int i = 1; i <= N; i += 2) {
            boolean curVal = cur.get(i - 1);
            nex.set(i - 1, !curVal);
        }
        return nex;
    }
    
    public static BitSet buttonThree(BitSet cur) {
        BitSet nex = (BitSet) cur.clone();
        for (int i = 2; i <= N; i += 2) {
            boolean curVal = cur.get(i - 1);
            nex.set(i - 1, !curVal);
        }
        return nex;
    }
    
    public static BitSet buttonFour(BitSet cur) {
        BitSet nex = (BitSet) cur.clone();
        for (int i = 1; i <= N; i += 3) {
            boolean curVal = cur.get(i - 1);
            nex.set(i - 1, !curVal);
        }
        return nex;
    }
}