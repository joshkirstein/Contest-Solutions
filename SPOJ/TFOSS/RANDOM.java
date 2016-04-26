import java.util.*;

class RANDOM {

    public static Random rnd = new Random();
    public static void main(String[] args) {
	int cases = 10000;
	System.out.println(cases);
	for (int i = 0; i < cases; i++) {
	    System.out.println();
	    int numbs = 1 + rnd.nextInt(2000);
	    System.out.println(numbs);
	    HashSet<Point> pts = new HashSet<Point>();
	    for (int j = 0; j < numbs; j++) {
		int x, y;
		//do {
		 x = rnd.nextInt(100000000)-50000000;
		 y = rnd.nextInt(100000000)-50000000;
		 //} while (pts.contains(new Point(x, y)));
		System.out.println(x + " " + y);
		//pts.add(new Point(x, y));
	    }
        }
    }
    static class Point {
	public int x, y;
	public Point(int x, int y) {
	    this.x = x;this.y=y;
	}
	public int hashCode() {
	    return 23*x+27*y;
	}
	public boolean equals(Object o) {
	    Point pt = (Point) o;
	    return pt.x == x && pt.y == y;
	}
    }
}
