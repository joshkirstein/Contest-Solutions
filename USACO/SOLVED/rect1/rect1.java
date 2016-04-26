
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
    
    public static boolean DEBUG = false;
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
        public String toString() {
            return "(LLX: " + llx + ", LLY: " + lly + ") TO (URX: " + urx + ", URY: " + ury + ") COLOR: " + color;
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
            addRect(r);
        }
        int[] colors = new int[2501];
        for (Rect re : rects) {
            colors[re.color] +=(Math.abs(re.llx-re.urx)*Math.abs(re.lly-re.ury));
        }
        for (int i = 0; i < 2501; i++) {
            if (colors[i] != 0) {
                out.append(i + " " + colors[i] + "\n");
            }
        }
        out.close();
    }
    
    public static void addRect(Rect r) {
        //add rectangle to rects
        if (rects.isEmpty()) {
            rects.add(r);
        } else {
            //handle collisions
            ArrayList<Rect> toAdd = new ArrayList<Rect>();
            toAdd.add(r);
            for (int i = 0; i < rects.size(); i++) {
                Rect get = rects.get(i);
                if (intersect(get, r)) {
                    rects.remove(i);
                    i--;
                    
                    //split TODO
                    /*Rect left = new Rect(get.llx, get.lly, r.llx, get.ury, get.color);
                    Rect right = new Rect(r.urx, get.lly, get.urx, get.ury, get.color);
                    Rect bot = new Rect(r.llx, get.lly, Math.min(r.urx, get.urx), r.lly, get.color);
                    Rect top = new Rect(r.llx, r.ury, r.urx, get.ury, get.color);*/
                    /** bottom **/
                    Rect bot = null;
                    //top line = (r.llx, r.lly) -> (r.urx, r.lly)
                    //bot line = (get.llx, get.lly) -> (get.urx, get.lly)
                    if (get.lly < r.lly) {//can make bot
                        bot = new Rect(Math.max(r.llx, get.llx), get.lly, Math.min(r.urx, get.urx), r.lly, get.color);
                    }
                    /** top **/
                    Rect top = null;
                    //top line = (get.llx, get.ury) -> (get.urx, get.ury)
                    //bot line = (r.llx, r.ury) -> (r.urx, r.ury)
                    if (get.ury > r.ury) {//can make bot
                        top = new Rect(Math.max(get.llx, r.llx), r.ury, Math.min(get.urx, r.urx), get.ury, get.color);
                    }
                    /** left **/
                    Rect left = null;
                    //left line = (get.llx, get.lly) -> (get.llx, get.ury)
                    //right line = (r.llx, r.lly) -> (r.llx, r.ury)
                    if (get.llx < r.llx) {//can make left
                        left = new Rect(get.llx, get.lly, r.llx, get.ury, get.color);
                    }
                    /** right **/
                    Rect right = null;
                    //left line = (r.urx, r.lly) -> (r.urx, r.ury)
                    //right line = (get.urx, get.lly) -> (get.urx, get.ury)
                    if (get.urx > r.urx) {//can make right
                        right = new Rect(r.urx, get.lly, get.urx, get.ury, get.color);
                    }
                    if (DEBUG) {
                        System.out.println("*****SPLITTING*****");
                        System.out.println("T: " + r);
                        System.out.println("B: " + get);
                    }
                    //Validity
                    if (valid(left)) {
                        if (DEBUG)
                            System.out.println("LEFT: " + left);
                        toAdd.add(left);
                    }
                    if (valid(right)) {
                        if (DEBUG)
                            System.out.println("RIGHT: " + right);
                        toAdd.add(right);
                    }
                    if (valid(bot)) {
                        if (DEBUG)
                            System.out.println("BOT: " + bot);
                        toAdd.add(bot);
                    }
                    if (valid(top)) {
                        if (DEBUG)
                            System.out.println("TOP: " + top);
                        toAdd.add(top);
                    }
                }
            }
            rects.addAll(toAdd);
        }
        if (DEBUG) {
            System.out.println("RECTS AFTER ITERATION: ");
            System.out.println(rects);
        }
    }
    
    public static boolean valid(Rect r) {
        return r != null && r.llx <= r.urx && r.lly <= r.ury && (Math.abs(r.llx-r.urx)*Math.abs(r.lly-r.ury))>0;
    }
    
    public static boolean intersect(Rect one, Rect two) {
        return (one.llx < two.urx && one.urx > two.llx && one.lly < two.ury && one.ury > two.lly);
    }
    
    public static boolean isPoint(Rect one, int x, int y) {
        return x >= one.llx && x <= one.urx && y >= one.lly && y <= one.ury;
    }
}