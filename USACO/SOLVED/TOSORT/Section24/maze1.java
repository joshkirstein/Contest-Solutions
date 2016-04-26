package Section24;


/*
ID: joshkir1
LANG: JAVA
TASK: maze1
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class maze1 {
    
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, -1, 0, 1 };
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("maze1.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("maze1.out"));
        //input processing output here
        String[] spl = sc.readLine().split(" ");
        int W = Integer.parseInt(spl[0]);
        int H = Integer.parseInt(spl[1]);
        char[][] gd = new char[2*H+1][2*W+1];
        int[][] dist = new int[2*H+1][2*W+1];
        int[][] start = new int[2][2];
        int z = 0;
        for (int i = 0; i < 2*H+1; i++) {
            Arrays.fill(dist[i], -1);
            char[] ar = sc.readLine().toCharArray();
            gd[i] = ar;
            if (i == 0 || i == 2*H) {
                for (int j = 0; j < ar.length; j++) {//check horizontal borders
                    if (ar[j] == ' ') {
                        start[z][0] = i;
                        start[z++][1] = j;
                    }
                }
            } else {
                if (ar[0] == ' ') {//check two ends horizontally
                    start[z][0] = i;
                    start[z++][1] = 0;
                } else if (ar[2*W] == ' ') {
                    start[z][0] = i;
                    start[z++][1] = 2*W;
                }
            }
        }
        class Pt {
            public int x, y;
        }
        Pt p1 = new Pt();
        p1.x = start[0][0];
        p1.y = start[0][1];
        Pt p2 = new Pt();
        p2.x = start[1][0];
        p2.y = start[1][1];
        Queue<Pt> q = new LinkedList<Pt>();
        q.add(p1);
        q.add(p2);
        dist[start[0][0]][start[0][1]] = 1;
        dist[start[1][0]][start[1][1]] = 1;
        int max = 0;
        while (!q.isEmpty()) {
            Pt pop = q.poll();
            for (int i = 0; i < 4; i++) {//find adjacent
                int dX = pop.x + dx[i];
                int dY = pop.y + dy[i];
                try {
                    if (gd[dX][dY] == ' ') {
                        //valid move...
                        if (dist[dX][dY] == -1) {//hasnt been seen yet...
                            dist[dX][dY] = dist[pop.x][pop.y] + 1;
                            max = Math.max(max, dist[dX][dY]);
                            Pt pt3 = new Pt();
                            pt3.x = dX;
                            pt3.y = dY;
                            q.add(pt3);
                        }
                    }
                } catch (Exception ex) { }
            }
            //print(dist);
        }
        System.out.println(max/2);
        out.append(max/2 + "\n");
        out.close();
    }
    
    public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}