import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HorseShoe {
    
    static class Point {
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
    
    public static int max = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("hshoe.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("hshoe.out"));
        int N = Integer.parseInt(sc.readLine());
        char[][] grid = new char[N][N];
        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            char[] NEW = sc.readLine().toCharArray();
            grid[i] = NEW;
        }
        Point start = new Point(0, 0);
        visited[0][0] = true;
        recurse(start, grid, visited, grid[0][0] == '(' ? 1 : 0, grid[0][0] == '(' ? 0 : 1);
        out.append(max + "\n");
        out.close();
    }
    
    final static int[][] move = {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 }
    };
    
    public static void recurse(Point pos, final char[][] grid, boolean[][] visited, int numleft, int numright) {
        //System.out.println("Numleft: " + numleft + " Numright: " + numright);
        if (numleft == numright) {
            //System.out.println("numleft: " + numleft);
            max = Math.max(max, numleft + numright);
        } else {//not done
            //still possible
            for (int i = 0; i < move.length; i++) {
                Point newP = new Point(pos.x + move[i][0], pos.y + move[i][1]);
                try {
                    if (!visited[newP.x][newP.y]) {
                        if (grid[newP.x][newP.y] == '(' && numright == 0) {//we can't start the right to go to another (
                            if (numleft == 3) {
                                //System.out.println("POS: " + pos);
                                //System.out.println("NEXT: " + newP);
                            }
                            int newLeft = numleft + 1;//this cant cause a problem
                            visited[newP.x][newP.y] = true;
                            recurse(newP, grid, visited, newLeft, numright);
                            visited[newP.x][newP.y] = false;        
                        } else if (grid[newP.x][newP.y] == ')') {
                            int newRight = numright + 1;
                            visited[newP.x][newP.y] = true;
                            recurse(newP, grid, visited, numleft, newRight);
                            visited[newP.x][newP.y] = false;   
                        }
                    }
                } catch (Exception ex) { }
            }
        }
    }
}
