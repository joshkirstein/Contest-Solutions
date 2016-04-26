/*
ID: joshkir1
LANG: JAVA
TASK: checker
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class checker {
    
    static BufferedWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("checker.in"));
        out = new BufferedWriter(new FileWriter("checker.out"));
        //input processing output here
        int N = Integer.parseInt(sc.readLine());
        int[] rowToCol = new int[N];//0...(N-1)
        int[] colToRow = new int[N];
        Arrays.fill(rowToCol, -1);
        Arrays.fill(colToRow, -1);
        dfs(rowToCol, colToRow, new boolean[200], new boolean[200], 0);
        if (print == 2) {
            out.append(last.reverse() + "\n");
        }
        out.append(checker.N + "\n");
        out.close();
    }
    
    static int N = 0;
    static int print = 0;
    static StringBuilder last = null;
    
    public static void dfs(int[] rowToCol, int[] colToRow, boolean[] diag, boolean[] diagTwo, int row) throws IOException {
        //System.out.println(row);
        if (row == rowToCol.length) {
            StringBuilder br = new StringBuilder("");
            if (print < 3) {
                boolean firs = true;
                for (int i = 0; i < rowToCol.length; i++) {
                    if (firs) {
                        firs = false;
                        br.append((rowToCol[i] + 1) + "");
                    } else {
                        br.append(" " + (rowToCol[i] + 1));
                    }
                }
                out.append(br + "\n");
                print++;
                last = br;
            }
            if (rowToCol.length % 2 == 0) {
                N += 2;
            } else {
                N++;
            }
        } else {
            for (int i = 0; i < (rowToCol.length); i++) {
                int num = 999;
                if (rowToCol.length % 2 == 0) {
                    num = (rowToCol.length / 2) - 1;
                } else {
                    //TODO WHAT WHEN ITS ODD???
                }
                if (row == 0 && i > num) {
                    return;
                }
                if (colToRow[i] == -1 && !diag[i - row + 20] && !diagTwo[i + row]) {//constant time diag check, hint 3
                    rowToCol[row] = i;
                    colToRow[i] = row;
                    diag[i - row + 20] = true;
                    diagTwo[i + row] = true;
                    dfs(rowToCol, colToRow, diag, diagTwo, row + 1);
                    rowToCol[row] = -1;
                    colToRow[i] = -1;
                    diag[i - row + 20] = false;
                    diagTwo[i + row] = false;
                }
            }
        }
    }
    
    public static boolean noDiag(int[] rowToCol, int row, int col) {
        for (int i = 0; i < rowToCol.length; i++) {
            int rowX = i;
            int colX = rowToCol[rowX];
            if (colX == -1) break;
            if (Math.abs(rowX-row) == Math.abs(colX-col)) {
                return false;
            }
        }
        return true;
    }
}