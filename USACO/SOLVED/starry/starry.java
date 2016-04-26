/*
ID: joshkir1
LANG: JAVA
TASK: starry
*/
import java.io.*;
import java.util.*;

public class starry {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader("starry.in"));
        PrintWriter out = new PrintWriter(new FileWriter("starry.out"));
        int width = sc.nextInt();
        int height = sc.nextInt();
        char[][] grid = new char[height][width];

        for (int i = 0; i < height; i++) {
            String line = sc.next();
            for (int j = 0; j < width; j++) {

                grid[i][j] = line.charAt(j);
            }
        }
        char first = 'a';
        HashMap<Integer, ArrayList<Shape>> map = new HashMap<Integer, ArrayList<Shape>>();
        boolean[][] visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            MAIN:
            for (int j = 0; j < width; j++) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    Queue<Pair> q = new LinkedList<Pair>();
                    ArrayList<Pair> vis = new ArrayList<Pair>();
                    vis.add(new Pair(i,j));
                    q.add(new Pair(i,j));
                    visited[i][j] = true;
                    while (!q.isEmpty()) {
                        Pair pop = q.poll();
                        for (int z = 0; z < 8; z++) {
                            int DX = pop.x+dx[z];
                            int DY = pop.y+dy[z];
                            if (DX >= 0 && DX < height && DY >= 0 && DY < width) {
                                if (!visited[DX][DY] && grid[DX][DY] == '1') {
                                    visited[DX][DY] = true;
                                    vis.add(new Pair(DX, DY));
                                    q.add(new Pair(DX, DY));
                                }
                            }
                        }
                    }

                    int xMax = Integer.MIN_VALUE;
                    int xMin = Integer.MAX_VALUE;
                    int yMax = Integer.MIN_VALUE;
                    int yMin = Integer.MAX_VALUE;
                    for (Pair p : vis) {
                        xMax = Math.max(xMax, p.x);
                        xMin = Math.min(xMin, p.x);
                        yMax = Math.max(yMax, p.y);
                        yMin = Math.min(yMin, p.y);
                    }
                    int amt = vis.size();
                    //[xMin,xMax] x [yMin, yMax]
                    boolean[][] shape = new boolean[(xMax-xMin)+1][(yMax-yMin)+1];
                    for (Pair p : vis) {
                        shape[p.x-xMin][p.y-yMin] = true;
                    }
                    Shape shap = new Shape(shape, amt, vis);

                    if (!map.containsKey(amt)) {
                        map.put(amt, new ArrayList<Shape>());
                    }
                    ArrayList<Shape> get = map.get(amt);
                    for (Shape s : get) {
                        if (s.equivalent(shap)) {
                            s.bucket.add(shap);
                            continue MAIN;
                        }
                    }
                    get.add(shap);
                    shap.id = first;
                    first++;
                }
            }
        }
        //System.out.println(first);
        char[][] ou = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ou[i][j] = '0';
            }
        }
        for (Map.Entry<Integer, ArrayList<Shape>> shape : map.entrySet()) {
            ArrayList<Shape> buck = shape.getValue();
            for (Shape sh : buck) {
                for (Shape s : sh.bucket) {
                    for (Pair p : s.easy) {
                        ou[p.x][p.y] = sh.id;
                    }
                }
            }
        }
        for (char[] o : ou) {
            out.println(String.valueOf(o));
        }

        out.close();
    }
    static int[] dx = {0,1,0,-1,1,-1,1,-1};
    static int[] dy = {1,0,-1,0,1,-1,-1,1};
}
class Shape {
    boolean[][] shape;
    int size;
    char id;
    ArrayList<Pair> easy;
    ArrayList<Shape> bucket = new ArrayList<Shape>();
    public Shape(boolean[][] shape, int size, ArrayList<Pair> easy) {
        this.shape = shape;
        this.size = size;
        this.easy = easy;
        bucket.add(this);
    }
    public void print() {
        System.out.println("PRINTING SHAPE " + id);
        for (boolean[] b : shape) {
            String str = "";
            for (boolean bb : b) {
                if (bb) str += "T";
                else str += "F";
            }
            System.out.println(str);
        }
        System.out.println("==================");
    }
    public boolean equivalent(Shape other) {
        boolean good = false;
        good |= equals(shape, other.shape);
       // print();
        rotate();
       // print();
        good |= equals(shape, other.shape);
        rotate();
       // print();
        good |= equals(shape, other.shape);
        rotate();
       // print();
        good |= equals(shape, other.shape);
        flip();
       // print();
        good |= equals(shape, other.shape);
        rotate();
        good |= equals(shape, other.shape);
        rotate();
        good |= equals(shape, other.shape);
        rotate();
        good |= equals(shape, other.shape);
        return good;
    }
    public void flip() {
        boolean[][] next = new boolean[shape.length][shape[0].length];
        for (int i = 0; i < shape.length; i++) {
            //if (shape.length % 2 != 0 && (i == shape.length/2)) continue;
            int first = i;
            int second = shape.length-1-i;
            boolean[] temp = shape[first];
            shape[first] = shape[second];
            shape[second] = temp;
            for (int j = 0; j < shape[0].length; j++) {
                next[first][j] = shape[second][j];
                next[second][j] = shape[first][j];
            }
        }
        shape = next;
    }
    public void rotate() {
        boolean[][] next = new boolean[shape[0].length][shape.length];
        for (int j = 0; j < shape[0].length; j++) {
            for (int i = shape.length-1; i >= 0; i--) {
                next[j][(shape.length-1)-i] = shape[i][j];
            }
        }
        shape = next;
    }

    public static boolean equals(boolean[][] one, boolean[][] two) {
        if (one.length != two.length || one[0].length != two[0].length) return false;
        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < one[0].length; j++) {
                if (one[i][j] != two[i][j]) return false;
            }
        }
        return true;
    }
}
class Pair {
    int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}