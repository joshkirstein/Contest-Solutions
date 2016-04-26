package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: satpix
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class satpix {
    
    static final char DFS_WHITE = '*';
    static final char DFS_GRAY = '-';
    static final char DFS_BLACK = 'x';
    static char[][] grid;
    static int W, H;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("satpix.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("satpix.out"));
        String[] inp = sc.readLine().split(" ");
        W = Integer.parseInt(inp[0]);
        H = Integer.parseInt(inp[1]);
        grid = new char[H][W];
        for (int i = 0; i < H; i++) {
            String read = sc.readLine();
            for (int j = 0; j < W; j++) {
                grid[i][j] = read.charAt(j);
            }
        }
        int max = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == DFS_WHITE) {
                    max = Math.max(max, dfs(i, j));
                }
            }
        }
        //System.out.println(max);
        out.append(max + "");
        out.append("\n");
        out.close();
        System.exit(0);
    }
    
    static int[][] arr =
    {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 }
    };
    
    public static int dfs(int x, int y) {
        grid[x][y] = DFS_GRAY;
        int count = 0;
        for (int[] a : arr) {
            int deltX = x + a[0];
            int deltY = y + a[1];
            try {
                if (grid[deltX][deltY] == DFS_WHITE) {
                    count += dfs(deltX, deltY);
                }
            } catch (Exception ex) { }//aiobe
        }
        grid[x][y] = DFS_BLACK;
        return count + 1;
    }
}
