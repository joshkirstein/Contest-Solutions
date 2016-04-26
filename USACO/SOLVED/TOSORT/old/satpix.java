package old;

/*
ID: joshkir1
LANG: JAVA
TASK: satpix
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class satpix {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("satpix.in")));
        PrintWriter out = new PrintWriter(new FileWriter("satpix.out"));
        String[] spl = sc.nextLine().split(" ");
        int W = Integer.parseInt(spl[0]);
        int H = Integer.parseInt(spl[1]);
        char[][] arr = new char[H][W];
        for (int i = 0; i < H; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < W; j++) {
                arr[i][j] = line.charAt(j);
            }
        }
        boolean[][] visited = new boolean[H][W];
        int max = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j] && arr[i][j] == '*') {
                    int count = 0;
                    Stack<Point> s = new Stack<Point>();
                    s.push(new Point(i, j));
                    while (!s.isEmpty()) {
                        Point pop = s.pop();
                        visited[pop.x][pop.y] = true;
                        count++;
                        int[][] adj = {
                            {0, 1},
                            {0, -1},
                            {1, 0},
                            {-1, 0}
                        };
                        for (int[] a : adj) {
                            int dX = pop.x + a[0];
                            int dY = pop.y + a[1];
                            if (dX >= 0 && dX < H && dY >= 0 && dY < W) {
                                if (!visited[dX][dY] && arr[dX][dY] == '*') {
                                    s.push(new Point(dX, dY));
                                    visited[dX][dY] = true;
                                }
                            }
                        }
                    }
                    max = Math.max(max, count);
                }
            }
        }
        out.println(max);
        out.close();
        System.exit(0);
    }

    static class Point {

        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return x == p.x && y == p.y;
            }
            return false;
        }
    }
}
