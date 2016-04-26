/*
ID: joshkir1
LANG: JAVA
TASK: fc
*/
import java.io.*;
import java.util.*;

class fc
{
	static PrintWriter out;
	public static void main(String[] args) throws IOException
	{
		BufferedReader sc = new BufferedReader(new FileReader("fc.in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter("fc.out")));
		int pts = Integer.parseInt(sc.readLine());
		Point[] pt = new Point[pts];
		for (int i = 0; i < pts; i++) {
			String[] spl = sc.readLine().split(" ");
			pt[i] = new Point(Double.parseDouble(spl[0]), Double.parseDouble(spl[1]));
		}
		Point[] hull = convexHull(pt);
		double sum = 0.0;
		for (int i = 0; i < hull.length; i++) {
			Point cur = hull[i];
			Point nex = hull[(i+1)%hull.length];
			sum += Math.sqrt((cur.x-nex.x)*(cur.x-nex.x)+(cur.y-nex.y)*(cur.y-nex.y));
		}
		out.println(String.format("%.2f", sum));
		out.close();
		System.exit(0);
	}

	//returns the convex hull of a set of points.
static Point[] convexHull(Point[] points) {
    if (points.length <= 3) return points;
    Arrays.sort(points); // sorts by x then y.
    Point[] upper = new Point[points.length];
    int upperIdx = 0;
    for (int i = 0; i < points.length; i++) {
    	while (upperIdx >= 2 &&
    		!leftOrStraight(upper[upperIdx-2], upper[upperIdx-1], points[i])) 
    		upperIdx--;
    	upper[upperIdx++] = points[i];
    }
    Point[] lower = new Point[points.length];
    int lowerIdx = 0;
    for (int i = points.length - 1; i >= 0; i--) {
    	while (lowerIdx >= 2 &&
    		!leftOrStraight(lower[lowerIdx-2], lower[lowerIdx-1], points[i]))
    		lowerIdx--;
    	lower[lowerIdx++] = points[i];
    }
    Point[] hull = new Point[upperIdx+lowerIdx-2];
    for (int i = 0; i < upperIdx; i++) hull[i] = upper[i];
    int next = upperIdx;
    for (int i = 1; i < lowerIdx-1; i++) hull[next++] = lower[i];
    return hull;
}

//either collinear or a left turn.
//if you don't want collinear points, remove the =
static boolean leftOrStraight(Point a, Point b, Point c) {
   	return ccw(a, b, c) >= 0;
}
    
//=0 is collinear, > 0 is left, <0 is right.
//this is just the cross product of the vectors ab and bc.
static double ccw(Point a, Point b, Point c) {
	return (b.x-a.x)*(c.y-b.y)-(c.x-b.x)*(b.y-a.y);
}
}
class Point implements Comparable<Point> {
	public double x, y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public int compareTo(Point p) {
		if (x > p.x) return 1;
		if (x < p.x) return -1;
		if (y > p.y) return 1;
		if (y < p.y) return -1;
		return 0;
	}
}