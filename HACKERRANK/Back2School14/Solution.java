import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(br.readLine());
        MAIN:
        while (cases-->0) {
            String[] spl = br.readLine().split(" ");
            int N = Integer.parseInt(spl[0]);
            int M = Integer.parseInt(spl[1]);
            String[] grid = new String[N];
            for (int i = 0; i < grid.length; i++) {
                String line = br.readLine();
                grid[i] = line;
            }
            String[] val = br.readLine().split(" ");
            int PN = Integer.parseInt(val[0]);
            int PM = Integer.parseInt(val[1]);
            String[] pattern = new String[PN];
            ArrayList[] a = new ArrayList[PN];
            for (int i = 0; i < PN; i++) {
                String line = br.readLine();
                pattern[i] = line;
                RabinKarp rk = new RabinKarp(line);
                ArrayList<Point> first = new ArrayList<Point>();
                int z = 0;
                for (String s : grid) {
                    ArrayList<Integer> res = rk.search(s);
                    for (Integer x : res) {
                        first.add(new Point(z, x));
                    }
                    z++;
                }
                a[i] = first;
            }
            ArrayList first = a[0];
            for (Object o : first) {
                Point val1 = (Point) o;
                boolean works = true;
                int col = val1.y;
                int i = 1;
                //System.out.println("MATCHED PATTERN ROW: " + val1.x + " AT COLUMN: " + val1.y);
                for (ArrayList sec : a) {
                    if (sec != first) {
                        //need to find 
                        boolean found = false;
                        for (Object so : sec) {
                            Point valSo = (Point) so;
                            if (valSo.y == val1.y && (valSo.x-i) == val1.x) {
                                found = true;
                            }
                        }
                        if (!found) {
                            works = false;
                            break;
                        }

                        i++;
                    }
                }


                if (works) {
                    System.out.println("YES");
                    continue MAIN;
                }
            }
            System.out.println("NO");
        }
    }
   
}
class RabinKarp {
    private String pat;      // the pattern  // needed only for Las Vegas
    private long patHash;    // pattern hash value
    private int M;           // pattern length
    private long Q;          // a large prime, small enough to avoid long overflow
    private int R;           // radix
    private long RM;         // R^(M-1) % Q

    public RabinKarp(int R, char[] pattern) {
        throw new UnsupportedOperationException("Operation not supported yet");
    }

    public RabinKarp(String pat) {
        this.pat = pat;      // save pattern (needed only for Las Vegas)
        R = 256;
        M = pat.length();
        Q = longRandomPrime();

        // precompute R^(M-1) % Q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= M-1; i++)
           RM = (R * RM) % Q;
        patHash = hash(pat, M);
    } 

    // Compute hash for key[0..M-1]. 
    private long hash(String key, int M) { 
        long h = 0; 
        for (int j = 0; j < M; j++) 
            h = (R * h + key.charAt(j)) % Q; 
        return h; 
    } 

    // Las Vegas version: does pat[] match txt[i..i-M+1] ?
    private boolean check(String txt, int i) {
        for (int j = 0; j < M; j++) 
            if (pat.charAt(j) != txt.charAt(i + j)) 
                return false; 
        return true;
    }

    // Monte Carlo version: always return true
    private boolean check(int i) {
        return true;
    }

    // check for exact match
    public ArrayList<Integer> search(String txt) {
        int N = txt.length(); 
        if (N < M) return new ArrayList<Integer>();
        long txtHash = hash(txt, M); 

        // check for match at offset 0
        if ((patHash == txtHash) && check(txt, 0))
            return new ArrayList<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        // check for hash match; if hash match, check for exact match
        for (int i = M; i < N; i++) {
            // Remove leading digit, add trailing digit, check for match. 
            txtHash = (txtHash + Q - RM*txt.charAt(i-M) % Q) % Q; 
            txtHash = (txtHash*R + txt.charAt(i)) % Q; 

            // match
            int offset = i - M + 1;
            if ((patHash == txtHash) && check(txt, offset)) {
                list.add(offset);
            }
        }

        // no match
        return list;
    }


    // a random 31-bit prime
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
}
class Point {
    public int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}