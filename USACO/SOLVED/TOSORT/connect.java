/*
ID: joshkir1
LANG: JAVA
TASK: connect
 */

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class connect {
    
    static class Point {
        public int x, y;
        public Point(int x, int y) { 
            this.x = x;
            this.y = y;
        }
    }
    
    public static int N;
    public static ArrayList<Point> cows;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("connect.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("connect.out"));
        cows = new ArrayList<Point>();
        N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            cows.add(new Point(sc.nextInt(), sc.nextInt()));
        }
        out.append(dfs(new Point(0, 0), new boolean[11], 0) + "");
        out.newLine();
        out.close();
    }
    
    public static int dfs(Point pt, boolean[] visited, int fin) {
        if (fin == N) {
            //if can reach 0, 0, return 1, else 0
            if (pt.x == 0 || pt.y == 0) {
                return 1;
            } else {
                return 0;   
            }
        } else {
            int res = 0;
            for (int i = 0; i < cows.size(); i++) {
                if (!visited[i]) {
                    Point p = cows.get(i);
                    if (p.x == pt.x) {//must have 1 coord equal to ours (PARALLEL TRAVEL);
                        visited[i] = true;
                        res += dfs(p, visited, fin + 1);
                        visited[i] = false;
                    } else if (p.y == pt.y) {
                        visited[i] = true;
                        res += dfs(p, visited, fin + 1);
                        visited[i] = false;
                    }
                }
            }
            return res;
        }
    }
}
