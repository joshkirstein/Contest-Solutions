import java.util.*;
import java.io.*;

class TFOSS {
    public static void main(String[] args) throws Exception {
		Parser p = new Parser(System.in);
		int cases = p.nextInt();
		while (cases-- > 0) {
			int pts = p.nextInt();
			Point[] ptset = new Point[pts];
			for (int i = 0; i < pts; i++) {
				ptset[i] = new Point(p.nextInt(), p.nextInt());
			}
			Point[] hull = convexHull(ptset);
			System.out.println(answer(hull));
		}
    }

    public static long abs(long l) {
    	if (l < 0) return -l;
    	return l;
    }
	public static double area(Point a, Point b, Point c) {
		return (0.5)*Math.abs(ccw(a, b, c));
	}


    public static long answer(Point[] hull) {
    	//Rotating calipers:
	    if (hull.length == 0 || hull.length == 1) return 0L;
	    if (hull.length == 2) return dist(hull[0], hull[1]);
	    long MAX = Long.MIN_VALUE;
	    int i = 0;
	   	int k = (i+2)%hull.length;
	    for (; i < hull.length; i++) {
			int j = (i+1)%hull.length;
	    	double curArea = area(hull[i], hull[j], hull[k]);
	    	long maxDist = dist(hull[i], hull[k]);
	    	//continually rotate k
		    while (area(hull[i], hull[j], hull[(k+1)%hull.length]) >= curArea) {
		    	curArea = area(hull[i], hull[j], hull[(k+1)%hull.length]);
		    	k = (k+1)%hull.length;
		    	maxDist = Math.max(maxDist, dist(hull[i], hull[k]));
		    }

		    MAX = Math.max(MAX, maxDist);
	    }
    	return MAX;
    }

    public static long dist(Point p1, Point p2) {
    	return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
    }

    public static Point[] convexHull(Point[] points) {
    	if (points.length <= 1) return points;
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
    	return ccw(a, b, c) >= 0;
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

class Parser
{
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
