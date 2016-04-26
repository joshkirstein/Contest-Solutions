
/*
ID: joshkir1
LANG: JAVA
TASK: rect1
*/
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class rect1 {
    
    static class Rect {
        public int llx, lly, urx, ury;
        public int color;
        public Rect(int llx, int lly, int urx, int ury, int color) {
            this.llx = llx;
            this.lly = lly;
            this.urx = urx;
            this.ury = ury;
            this.color = color;
        }
    }
    
    static ArrayList<Rect> rects = new ArrayList<Rect>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("rect1.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("rect1.out"));
        int A = sc.nextInt(), B = sc.nextInt(), N = sc.nextInt();
        Rect base = new Rect(0, 0, A, B, 1);//base rectangle o
        addRect(base);
        for (int i = 0; i < N; i++) {
            int llx = sc.nextInt(), lly = sc.nextInt(), urx = sc.nextInt(), ury = sc.nextInt(), color = sc.nextInt();
            Rect r = new Rect(llx, lly, urx, ury, color);
            
        }
        out.close();
    }
    
    public static void addRect(Rect r) {
        Iterator<Rect> it = rects.iterator();
        toAdd = new ArrayList<Rect>();
        while (it.hasNext()) {
            Rect pop = it.next();
            //handle collisions
            //if collision
            //remove...etc-
            if (handleCollision(r, pop)) {
                it.remove();
            }
        }
        rects.addAll(toAdd);
        rects.add(r);
    }
    
    static ArrayList<Rect> toAdd;
    
    public static boolean handleCollision(Rect r1, Rect r2) {
        int[][] arr = new int[4][4];
        //lower:
        
        return false;
    }
}