
/*
ID: joshkir1
LANG: JAVA
TASK: packrec
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class packrec {
    
    static class Rectangle implements Comparable<Rectangle> {
        public int l,w;//l = upper, w == lower (doesn't matter, easier to think about it)
        public Rectangle(int l, int w) {
            this.l = l;
            this.w = w;
        }

        public int compareTo(Rectangle o) {
            return l - o.l;
        }
        
        public String toString() {
            return l + " " + w;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + this.l;
            hash = 79 * hash + this.w;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Rectangle other = (Rectangle) obj;
            if (this.l != other.l) {
                return false;
            }
            if (this.w != other.w) {
                return false;
            }
            return true;
        }
    }
    
    static int minArea = Integer.MAX_VALUE;
    static Set<Rectangle> soln = new HashSet<Rectangle>();//Solution is the encapsulating rectangle, subject to l <= w
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("packrec.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("packrec.out"));
        Rectangle[] rct = new Rectangle[4];
        String[] l1 = sc.readLine().split(" ");
        String[] l2 = sc.readLine().split(" ");
        String[] l3 = sc.readLine().split(" ");
        String[] l4 = sc.readLine().split(" ");
        rct[0] = new Rectangle(Integer.parseInt(l1[0]), Integer.parseInt(l1[1]));
        rct[1] = new Rectangle(Integer.parseInt(l2[0]), Integer.parseInt(l2[1]));
        rct[2] = new Rectangle(Integer.parseInt(l3[0]), Integer.parseInt(l3[1]));
        rct[3] = new Rectangle(Integer.parseInt(l4[0]), Integer.parseInt(l4[1]));
        //calculate solutions here
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                for (int k = 0; k <= 1; k++) {
                    for (int l = 0; l <= 1; l++) {
                        //0 = stay, 1 = reverse
                        //DO FLIPS:
                        if (i == 1) {
                            int temp = rct[0].l;
                            rct[0].l = rct[0].w;
                            rct[0].w = temp;
                        }
                        if (j == 1) {
                            int temp = rct[1].l;
                            rct[1].l = rct[1].w;
                            rct[1].w = temp;
                        }
                        if (k == 1) {
                            int temp = rct[2].l;
                            rct[2].l = rct[2].w;
                            rct[2].w = temp;
                        }
                        if (l == 1) {
                            int temp = rct[3].l;
                            rct[3].l = rct[3].w;
                            rct[3].w = temp;
                        }
                        algorithm(rct);
                        //REVERSE FLIPS:
                        if (i == 1) {
                            int temp = rct[0].l;
                            rct[0].l = rct[0].w;
                            rct[0].w = temp;
                        }
                        if (j == 1) {
                            int temp = rct[1].l;
                            rct[1].l = rct[1].w;
                            rct[1].w = temp;
                        }
                        if (k == 1) {
                            int temp = rct[2].l;
                            rct[2].l = rct[2].w;
                            rct[2].w = temp;
                        }
                        if (l == 1) {
                            int temp = rct[3].l;
                            rct[3].l = rct[3].w;
                            rct[3].w = temp;
                        }
                    }
                }
            }
        }
        //done w/ calc solutions
        ArrayList<Rectangle> solns = new ArrayList<Rectangle>(soln);
        Collections.sort(solns);
        out.append(minArea + "\n");
        for (Rectangle rect : solns) {
            out.append(rect + "\n");
        }
        out.close();
    }
    
    public static void algorithm(Rectangle[] rects) {
        //Find optimal solutions for this set of rectangle configurations
        //l = top and bottom, w = right and left
        //CONFIGURATION (Order matters, permute):
        recurse(rects, new boolean[4], -1, -1, -1, -1);
    }
    
    public static void recurse(final Rectangle[] pool, boolean[] used, int i, int j, int k, int l) {
        if (l != -1) {
            //rectangle 0 = pool[i]
            //Rectangle 1 = pool[j]
            //...etc
            Rectangle[] rects = new Rectangle[4];
            rects[0] = pool[i];
            rects[1] = pool[j];
            rects[2] = pool[k];
            rects[3] = pool[l];
            firstConfig(rects);
            secondConfig(rects);
            thirdConfig(rects);
            fourthConfig(rects);
            fifthConfig(rects);
            sixthConfig(rects); //only possible overlap on six!
        } else {
            //still gotta recurse
            for (int z = 0; z <= 3; z++) {
                if (!used[z]) {
                    used[z] = true;
                    if (i == -1) {
                        recurse(pool, used, z, j, k, l);
                    } else if (j == -1) {
                        recurse(pool, used, i, z, k, l);
                    } else if (k == -1) {
                        recurse(pool, used, i, j, z, l);
                    } else if (l == -1) {
                        recurse(pool, used, i, j, k, z);
                    }
                    used[z] = false;
                }
            }
        }
    }
    
    public static void firstConfig(Rectangle[] rects) {
        int length = rects[0].l + rects[1].l + rects[2].l + rects[3].l;
        int width = Math.max(Math.max(Math.max(rects[0].w, rects[1].w), rects[2].w), rects[3].w);
        if (length * width < minArea) {
            minArea = length * width;
            soln.clear();
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        } else if (length * width == minArea) {
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        }
    }
    
    public static void secondConfig(Rectangle[] rct) {
        int length = Math.max(rct[0].l, rct[1].l + rct[2].l + rct[3].l);
        int width = rct[0].w + Math.max(rct[1].w, Math.max(rct[2].w, rct[3].w));
        if (length * width < minArea) {
            minArea = length * width;
            soln.clear();
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        } else if (length * width == minArea) {
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        }
    }
    
    public static void thirdConfig(Rectangle[] rct) {
        int length = rct[1].l + Math.max(rct[0].l, rct[2].l + rct[3].l);
        int width = Math.max(rct[1].w, rct[0].w + Math.max(rct[2].w, rct[3].w));
        if (length * width < minArea) {
            minArea = length * width;
            soln.clear();
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        } else if (length * width == minArea) {
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        }
    }
    
    public static void fourthConfig(Rectangle[] rct) {
        int length = rct[0].l + rct[2].l + Math.max(rct[1].l, rct[3].l);
        int width = Math.max(rct[0].w, Math.max(rct[2].w, rct[1].w + rct[3].w));
        if (length * width < minArea) {
            minArea = length * width;
            soln.clear();
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        } else if (length * width == minArea) {
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        }
    }
    
    public static void fifthConfig(Rectangle[] rct) {
        //same as fourth
    }
    
    public static void sixthConfig(Rectangle[] rct) {//not working?
        /*int length = 0;
        if (rct[2].w >= rct[1].w + rct[3].w) {
            length = Math.max(rct[0].l, Math.max(rct[1].l + rct[2].l, rct[2].l + rct[3].l));
        } else if (rct[2].w > rct[3].w && rct[2].w < rct[1].w + rct[3].w) {
            length = Math.max(Math.max(rct[0].l + rct[1].l, rct[1].l + rct[2].l), rct[2].l + rct[3].l);
        } else if (rct[2].w < rct[3].w && rct[3].w < rct[0].w + rct[2].w) {
            length = Math.max(Math.max(rct[0].l + rct[1].l, rct[0].l + rct[3].l), rct[2].l + rct[3].l);
        } else if (rct[3].w >= rct[0].w + rct[2].w) {
            length = Math.max(Math.max(rct[0].l + rct[3].l, rct[2].l + rct[3].l), rct[1].l);
        } else {
            length = Math.max(rct[0].l + rct[1].l, rct[2].l + rct[3].l);
        }
        if (length * width < minArea) {
            minArea = length * width;
            soln.clear();
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        } else if (length * width == minArea) {
            soln.add(new Rectangle(Math.min(length, width), Math.max(length, width)));
        }*/
    }
}