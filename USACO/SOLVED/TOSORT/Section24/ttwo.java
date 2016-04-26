package Section24;

/*
ID: joshkir1
LANG: JAVA
TASK: ttwo
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ttwo {
    
    static int[][] DELTA = {
        { -1, 0 },//north
        { 0, 1 },//east
        { 1, 0 },//south
        { 0, -1 }//west
    };
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("ttwo.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("ttwo.out"));
        char[][] gd = new char[10][10];
        boolean[][][][][][] vis = new boolean[10][10][10][10][4][4];
        int fX = -1, fY = -1, fdir = 0;//0 = north, 1 = east, 2 = south, 3 = west
        int cX = -1, cY = -1, cdir = 0;
        for (int i = 0; i < 10; i++) {
            char[] s = sc.readLine().toCharArray();
            gd[i] = s;
            for (int j = 0; j < 10; j++) {
                if (s[j] == 'C') {
                    cX = i;
                    cY = j;
                    s[j] = '.';
                } else if (s[j] == 'F') {
                    fX = i;
                    fY = j;
                    s[j] = '.';
                }
            }
        }
        int i;
        for (i = 0; vis[fX][fY][cX][cY][fdir][cdir] == false; i++) {
            if (cX == fX && cY == fY) break;
            vis[fX][fY][cX][cY][fdir][cdir] = true;
            int fDX = fX + DELTA[fdir][0];
            int fDY = fY + DELTA[fdir][1];
            if (turn(fDX, fDY, gd)) {
                fdir = (fdir + 1) % 4;
            } else {
                fX = fDX;
                fY = fDY;
            }
            int cDX = cX + DELTA[cdir][0];
            int cDY = cY + DELTA[cdir][1];
            if (turn(cDX, cDY, gd)) {
                cdir = (cdir + 1) % 4;
            } else {
                cX = cDX;
                cY = cDY;
            }
            gd[cX][cY] = 'C';
            gd[fX][fY] = 'F';
            /*System.out.println("------");
            for (char[] arr : gd) {
                for (char c : arr) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println("------");
            gd[cX][cY] = '.';
            gd[fX][fY] = '.';*/
        }
        if (vis[fX][fY][cX][cY][fdir][cdir]) {
            out.append("0\n");
            System.out.println(i);
        } else {
            out.append(i + "\n");
        }
        out.close();
    }
    
    public static boolean turn(int x, int y, char[][] gd) {
        try {
            if (gd[x][y] == '*') {
                return true;//moving into obstacle
            }
        } catch (Exception ex) {
            return true;//going out of board
        }
        return false;//can move
    }
}