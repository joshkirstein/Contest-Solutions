import java.util.*;
import java.io.*;

class DRAWQUAD {
	
	public static void main(String[] args) throws Exception {
		Parser sc = new Parser(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == -1) return;
			Point[] pts = new Point[N];
			for (int i = 0; i < N; i++) {
				pts[i] = new Point(sc.nextInt(), sc.nextInt());
			}
			Point[] hull = convexHull(pts);
			System.out.printf("%.1f\n", soln(hull, pts));
		}
	}

	static double soln(Point[] hull, Point[] pts) {
		if (hull.length < 3) {
			//all points collinear
			//or not enough points
			return 0.0;
		} else if (hull.length == 3) {
			double max = 0.0;
			for (int i = 0; i < hull.length; i++) {
				for (int j = 0; j < hull.length; j++) {
					for (int k = 0; k < hull.length; k++) {
						for (int l = 0; l < pts.length; l++) {
							if (i != j && j != k && i != k) {
								Point x = hull[i];
								Point y = hull[j];
								Point z = hull[k];
								Point m = pts[l];
								if (x != m && y != m && z != m) {
									max = Math.max(max, area(new Point[] { x, y, z, m }));
									max = Math.max(max, area(new Point[] { x, y, m, z }));
									max = Math.max(max, area(new Point[] { x, m, y, z }));
									max = Math.max(max, area(new Point[] { m, x, y, z }));
								}
							}
						}
					}
				}
			}
			return max;
		} else {
			//Going to be on hull
		    // "Rotating calipers":
			double MAX_AREA = Double.MIN_VALUE;
			int i = 0;
			int j = (i+1)%hull.length;//Having these variables out here makes it O(n)
			int k = (i+2)%hull.length;
			int l = (i+3)%hull.length;
			for (; i < hull.length; i++) { // Go through each point
			    // when we have '>=' we can end up on the same points which is bad.
			    if (i == j) j = (j+1)%hull.length;
			    if (j == k) k = (k+1)%hull.length;
			    if (k == l) l = (l+1)%hull.length;
			    double curArea = area(new Point[] { hull[i], hull[j], hull[k], hull[l] });
			    while (true) {
			    	while (true) {
					    while (area(new Point[] { hull[i], hull[j], hull[k], hull[(l+1)%hull.length] }) >= curArea){
					    	curArea = area(new Point[] { hull[i], hull[j], hull[k], hull[(l+1)%hull.length] });
					    	l = (l+1)%hull.length;
					    }
					    if (area(new Point[] { hull[i], hull[j], hull[(k+1)%hull.length], hull[l] } ) >= curArea) {
					    	curArea = area(new Point[] { hull[i], hull[j], hull[(k+1)%hull.length], hull[l] } );
					    	k = (k+1)%hull.length;
					    } else {
					    	break;
					    }
			    	}
					if (area(new Point[] { hull[i], hull[(j+1)%hull.length], hull[k], hull[l] } ) >= curArea) {
				    	curArea = area(new Point[] { hull[i], hull[(j+1)%hull.length], hull[k], hull[l] } );
				    	j = (j+1)%hull.length;
				    } else {
				    	break;
				    }
			    }
			    MAX_AREA = Math.max(MAX_AREA, curArea);
			}
			return MAX_AREA;
		}
	}

	static double area(Point[] pts) {
		int n = pts.length;
		double ans = 0;
		for(int i = 0; i < n; ++i) {
			ans += pts[i].x * pts[(i + 1) % n].y;
			ans -= pts[(i + 1) % n].x * pts[i].y;
		}
		return Math.abs(ans / 2.0);
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
	static long ccw(Point a, Point b, Point c) {
		return (b.x-a.x)*(c.y-b.y)-(c.x-b.x)*(b.y-a.y);
	}
}
class Point implements Comparable<Point> {
	long x, y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int compareTo(Point pt) {
		if (x == pt.x) {
			if (y < pt.y) return -1;
			return 1;
		} else {
			if (x < pt.x) return -1;
			return 1;
		}
	}
}
class Parser {
	final private int BUFFER_SIZE = 1 << 16;

	private java.io.DataInputStream din;
	private byte[] buffer;
	private int bufferPointer, bytesRead;

	public Parser(java.io.InputStream in)
	{
		din = new java.io.DataInputStream(in);
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public int nextInt() throws Exception
	{
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = c == '-';
		if (neg)
			c = read();
		int ret = 0;
		do
		{
			ret = ret * 10 + c - '0';
			c = read();
		} while (c > ' ');
		bufferPointer--;
		if (neg)
			return -ret;
		return ret;
	}

	public long nextLong() throws Exception
	{
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = c == '-';
		if (neg)
			c = read();
		long ret = 0;
		do
		{
			ret = ret * 10 + c - '0';
			c = read();
		} while (c > ' ');
		bufferPointer--;
		if (neg)
			return -ret;
		return ret;
	}

	public double nextDouble() throws Exception
	{
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = c == '-';
		if (neg)
			c = read();
		boolean seenDot = false;
		double div = 1;
		double ret = 0;
		do
		{
			if (c == '.')
				seenDot = true;
			else
			{
				if (seenDot)
					div *= 10;
				ret = ret * 10 + c - '0';
			}
			c = read();
		} while (c > ' ');
		bufferPointer--;
		ret /= div;
		if (neg)
			return -ret;
		return ret;
	}

	public char nextChar() throws Exception
	{
		byte c = read();
		while (c <= ' ')
			c = read();
		return (char) c;
	}

	public String next() throws Exception
	{
		StringBuilder ret = new StringBuilder();
		byte c = read();
		while (c <= ' ')
			c = read();
		do
		{
			ret.append((char) c);
			c = read();
		} while (c > ' ');
		bufferPointer--;
		return ret.toString();
	}

	// read a string into an ALREADY ALLOCATED array, returns the number of characters read
	public int next(char[] ret) throws Exception
	{
		byte c = read();
		while (c <= ' ')
			c = read();
		int bRead = 0;
		do
		{
			ret[bRead++] = (char) c;
			c = read();
		} while (c > ' ');
		bufferPointer--;
		return bRead;
	}

	public String nextLine() throws Exception
	{
		StringBuilder ret = new StringBuilder();
		byte c = read();
		while (c != '\r' && c != '\n' && c != -1)
		{
			ret.append((char) c);
			c = read();
		}
		if (c == '\r')
			read();
		return ret.toString();
	}

	public boolean hasNext() throws Exception
	{
		byte c;
		do
		{
			c = read();
			if (c == -1)
			{
				bufferPointer--;
				return false;
			}
		} while (c <= ' ');
		bufferPointer--;
		return true;
	}

	public void close() throws Exception
	{
		din.close();
	}

	private void fillBuffer() throws Exception
	{
		bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
		if (bytesRead == -1)
			buffer[0] = -1;
	}

	private byte read() throws Exception
	{
		if (bufferPointer == bytesRead)
			fillBuffer();
		return buffer[bufferPointer++];
	}
}