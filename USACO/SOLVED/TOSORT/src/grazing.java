/*
ID: joshkir1
LANG: JAVA
TASK: grazing
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class grazing {
    
    static class Point {
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return "[" + x + "," + y + "]";
        }
        public boolean equals(Point o) {
            return (o.x == x && o.y == y);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("grazing.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("grazing.out"));
        int k = Integer.parseInt(sc.readLine());
        boolean[][] grid = new boolean[5][5];//false has, true doesnt.
        for (int i = 0; i < k; i++) {
            String[] inp = sc.readLine().split(" ");
            int x = Integer.parseInt(inp[0]) - 1;
            int y = Integer.parseInt(inp[1]) - 1;
            grid[x][y] = true;
        }
        grid[0][0] = true;//starting points have no grass
        grid[4][4] = true;
        out.append(run(new Point(0, 0), new Point(4, 4), 23 - k, grid) + "");
        out.newLine();
        out.close();
        //System.exit(0);
    }
    
    static final int[][] MOVES =
    {
        { 0, 1 },
        { 1, 0 },
        { 0, -1 },
        { -1, 0 }
    };
    
    public static int run(Point b, Point m, int k, boolean[][] grid) {
        if (k == 1) {
            for (int[] set : MOVES) {
                int dX = b.x + set[0];
                int dY = b.y + set[1];
                if (in(dX, dY) && !grid[dX][dY]) {
                    for (int[] set4m : MOVES) {
                        int mX = m.x + set4m[0];
                        int mY = m.y + set4m[1];
                        if (dX == mX && dY == mY) {
                            return 1;//They can both reach the last piece of grass, so we're done and this is a valid solution.
                        }
                    }
                }
            }
            return 0;
        } else {
            int res = 0;
            for (int[] set : MOVES) {
                int dX = b.x + set[0];
                int dY = b.y + set[1];
                if (in(dX, dY) && !grid[dX][dY]) {
                    for (int[] set4m : MOVES) {
                        int mX = m.x + set4m[0];
                        int mY = m.y + set4m[1];
                        if (in(mX, mY) && !grid[mX][mY] && (!new Point(dX, dY).equals(new Point(mX, mY)))) {
                            boolean[][] newGrid = new boolean[5][5];
                            for (int i = 0; i < grid.length; i++) {
                                for (int j = 0; j < grid[i].length; j++) {
                                    newGrid[i][j] = grid[i][j];
                                }
                            }
                            newGrid[dX][dY] = true;
                            newGrid[mX][mY] = true;
                            res += run(new Point(dX, dY), new Point(mX, mY), k - 2, newGrid);
                        }
                    }
                }
            }
            return res;
        }
    }
    
    public static boolean in(int x, int y) {
        return (x >= 0 && x <= 4) && (y >= 0 && y <= 4);
    }
}
