/*
ID: joshkir1
LANG: JAVA
TASK: wrongdir
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class wrongdir {
    
    static class Point {
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Point other = (Point) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + this.x;
            hash = 37 * hash + this.y;
            return hash;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("wrongdir.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("wrongdir.out"));
        char[] s = sc.readLine().toCharArray();
        int num = 0;
        HashSet<Point> points = new LinkedHashSet<Point>();
        for (int i = 0; i < s.length; i++) {
            char temp = s[i];
            Point p1 = null;
            Point p2 = null;
            if (temp == 'F') {
                s[i] = 'L';
                p1 = compute(s);
                s[i] = 'R';
                p2 = compute(s);
            } else if (temp == 'L') {
                s[i] = 'F';
                p1 = compute(s);
                s[i] = 'R';
                p2 = compute(s);
            } else {
                s[i] = 'F';
                p1 = compute(s);
                s[i] = 'L';
                p2 = compute(s);
            }
            s[i] = temp;
            points.add(p1);
            points.add(p2);
        }
        out.append(points.size() + "");
        out.newLine();
        out.close();
    }
    
    public static Point compute(char[] arr) {
        int dir = 0;//0 up, 1 right, 2 left, 3 down
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'R') {
                if (dir == 3) {
                    dir = 0;
                } else {
                    dir++;
                }
            } else if (arr[i] == 'L') {
                if (dir == 0) {
                    dir = 3;
                } else {
                    dir--;
                }
            } else { //'F'
                if (dir == 0) {
                    y++;
                } else if (dir == 1) {
                    x++;
                } else if (dir == 2) {
                    x--;
                } else if (dir == 3) {
                    y--;
                }
            }
        }
        return new Point(x, y);
    }
}
