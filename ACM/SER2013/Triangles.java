import java.util.*;
import java.io.*;

public class Triangles {
	static Random rnd = new Random();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == 0) break;
			Point[] pts = new Point[N];
			for (int i = 0; i < N; i++) {
				pts[i] = new Point(sc.nextInt(), sc.nextInt());
			}
			double max = answer(convexHull(pts));
			double min = Double.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				for (int j = i+1; j < (N-1); j++) {
					//[j+1, N]
					int size = N-(j+1);
					for (int k = 0; k < 100; k++) {
						int other = rnd.nextInt(size)+(j+1);
						Point p1 = pts[i];
						Point p2 = pts[j];
						min = Math.min(min, area(p1,p2, pts[other]));
					}
				}
			}
			System.out.printf("%.1f %.1f\n", min, max);
		}
	}


public static double area(Point a, Point b, Point c) {
		return (0.5)*Math.abs(ccw(a, b, c));
	}
	
	public static double answer(Point[] hull) {
    	//"Rotating calipers":
	    if (hull.length == 0 || hull.length == 1 || hull.length == 2) return 0L;
	    double MAX_AREA = Double.MIN_VALUE;
	    int i = 0;
		int j = (i+1)%hull.length;//Having these variables out here makes it O(n)
	    int k = (i+2)%hull.length;
	    for (; i < hull.length; i++) { // Go through each point
	    	if (i == j) j = (j+1)%hull.length;//when we have '>=' we can end up on the same points which is bad.
	    	if (j == k) k = (k+1)%hull.length;
	    	double curArea = area(hull[i], hull[j], hull[k]);
	    	while (true) { // Continually rotate j and k until we have maximum area triangle cornered at i.
		    	while (k != i && area(hull[i], hull[j], hull[(k+1)%hull.length]) >= curArea) {
		    		curArea = area(hull[i], hull[j], hull[(k+1)%hull.length]);
		    		k = (k+1)%hull.length;
		    	}
		    	if (j != i && area(hull[i], hull[(j+1)%hull.length], hull[k]) >= curArea) {
		    		curArea = area(hull[i], hull[(j+1)%hull.length], hull[k]);
		    		j = (j+1)%hull.length;
		    	} else {
		    		break;
		    	}
	    	}
	    	MAX_AREA = Math.max(MAX_AREA, curArea);
	    }
    	return MAX_AREA;
    }

	public static long dist(Point p1, Point p2) {
    	return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
    }

	public static Point[] convexHull(Point[] points) {
    	if (points.length == 1) return points;
    	Arrays.sort(points);
    	Point[] upper = new Point[points.length];
    	int upperIdx = 0;
    	for (int i = 0; i < points.length; i++) {
    		while (upperIdx >= 2 
    			&& !leftOrStraight(upper[upperIdx-2], upper[upperIdx-1], points[i])) 
    			upperIdx--;
    		upper[upperIdx++] = points[i];
    	}
    	Point[] lower = new Point[points.length];
    	int lowerIdx = 0;
    	for (int i = points.length - 1; i >= 0; i--) {
    		while (lowerIdx >= 2
    			&& !leftOrStraight(lower[lowerIdx-2], lower[lowerIdx-1], points[i]))
    			lowerIdx--;
    		lower[lowerIdx++] = points[i];
    	}
    	Point[] hull = new Point[upperIdx+lowerIdx-2];
    	for (int i = 0; i < upperIdx; i++) hull[i] = upper[i];
    	int next = upperIdx;
    	for (int i = 1; i < lowerIdx-1; i++) hull[next++] = lower[i];
    	return hull;
    }

    public static boolean leftOrStraight(Point a, Point b, Point c) {
    	return ccw(a, b, c) >= 0; // don't want collinear??
    }

    public static long ccw(Point a, Point b, Point c) {//=0 is collinear, > 0 is left, <0 is right.
    	return (b.x-a.x)*(c.y-b.y)-(c.x-b.x)*(b.y-a.y);
    }
}
class Point implements Comparable<Point> {
	public long x, y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int compareTo(Point p) {
		if (x == p.x) {
			if (y > p.y) return 1;
			else return -1;
		} else {
			if (x > p.x) return 1;
			else return -1;
		}
	}
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}