import java.util.*;
import java.io.*;
class TFOSS {
    public static void main(String[] args) throws Exception {
	Parser p = new Parser(System.in);
	int cases = p.nextInt();
        while (cases-- > 0) {
	    int N;
	    Point[] pts;
	    N = p.nextInt();
	    pts = new Point[N];
	    for (int i = 0; i < N; i++) {
		pts[i] = new Point(p.nextInt(), p.nextInt());
	    }
	    Arrays.sort(pts);
	    //System.out.println("SORTED: " + Arrays.toString(pts));
	    Point[] hull = new Point[N];
	    int idx = 0;
	    for (int i = 0; i < N; i++) {
		if (i != 0 && pts[i].equals(pts[i-1])) continue;
		if (idx < 2) {//less than two points in
		    hull[idx] = pts[i];
		    idx++;
		} else {
		    while (idx >= 2) {//at least two points
			Point back = hull[idx-2];
			Point mid = hull[idx-1];
			if (ccw(back, mid, pts[i]) >= 0) {//must make a left...
			    break;
			}
			idx--;
		    }
		    hull[idx] = pts[i];
		    idx++;
		}
	    }
	    int endOfLower = idx - 1;
	    for (int i = N-2; i > 0; i--) {
		//System.out.println(Arrays.toString(hull) + "IDX: " + idx + " @ " + pts[i]);
		if (i != N-2 && pts[i].equals(pts[i+1])) continue;
		if ((idx - endOfLower) < 2) {//# of things in the upper < 2
		    if (idx >= N) continue;
		    hull[idx] = pts[i];
		    idx++;
		} else {
		    while ((idx-endOfLower) >= 2) {
			Point back = hull[idx-2];
			Point mid = hull[idx-1];
			//System.out.println("TESTING: " + back + "->" + mid + "->" + pts[i]);
			if (ccw(back, mid, pts[i]) >= 0) {//must make a left...
			    break;
			}
		        idx--;
		    }
		    if (idx >= N) continue;
		    hull[idx] = pts[i];
		    idx++;
		}
	    }
	    //make sure last point is good:
	    while (idx >= 2 && ccw(hull[idx-2], hull[idx-1], hull[0]) < 0) idx--;
	    //System.out.println(Arrays.toString(hull) + " IDX: " + idx);
	    long diameterSq = Integer.MIN_VALUE;
	    int endOfHull = idx - 1;
	    int idxN1 = 0;//min x initially
	    int idxN2 = endOfLower;//max x initially
	    int init1 = idxN1;
	    diameterSq = dist(hull[idxN1], hull[idxN2]);
	    Vector N1DIR = new Vector(0, -1);//positive y-axis
	    Vector N2DIR = new Vector(0, 1);//negative y-axis
	    double n1Delt = 0.0;
	    double n2Delt = 0.0;
	    double angle = 0.0;
	    
	    //System.out.println(hull[idxN1] + "**->" + hull[idxN2]);
	    while (angle <= Math.PI) {//go until wrap around
		int N1Next = (idxN1+1)%(idx);
		int N2Next = (idxN2+1)%(idx);
		Vector edgeN1 = new Vector(hull[N1Next].x-hull[idxN1].x, hull[N1Next].y-hull[idxN1].y);
		Vector edgeN2 = new Vector(hull[N2Next].x-hull[idxN2].x, hull[N2Next].y-hull[idxN2].y);
		//angle between n1DIR and edgeN1
		double angleN1 = angle(N1DIR, edgeN1)-n1Delt;
		//angle between n2dIR and edgeN1
		double angleN2 = angle(N2DIR, edgeN2)-n2Delt;
		//System.out.println("ANGLEn1: " + angleN1);
		//System.out.println("ANGLEN2: " + angleN2);
		if (angleN1 < angleN2) {
		    idxN1 = N1Next;
		    N1DIR = edgeN1;
		    n1Delt = 0.0;
		    n2Delt += angleN1;
		} else if (angleN1 > angleN2) {
		    idxN2 = N2Next;
		    N2DIR = edgeN2;
		    n2Delt = 0.0;
		    n1Delt += angleN2;
		} else {
		    n1Delt = 0.0;
		    n2Delt = 0.0;
		    N1DIR = edgeN1;
		    N2DIR = edgeN2;
		    diameterSq = Math.max(diameterSq, dist(hull[idxN1], hull[N2Next]));
		    diameterSq = Math.max(diameterSq, dist(hull[idxN2], hull[N1Next]));
		    //System.out.println(hull[idxN1] + "*->" + hull[N2Next]);
		    //System.out.println(hull[idxN2] + "*->" + hull[N1Next]);
		    idxN1 = N1Next;
		    idxN2 = N2Next;
		}
		angle += Math.min(angleN1, angleN2);
		diameterSq = Math.max(diameterSq, dist(hull[idxN1], hull[idxN2]));
		//System.out.println(hull[idxN1] + "<->" + hull[idxN2]);
	    }
	    System.out.println(diameterSq);
	    long diameterSq2 = 0;
	    for (Point pz : pts) {
		for (Point pp : pts) {
		    diameterSq2 = Math.max(diameterSq2, dist(pz, pp));
		    if (dist(pz, pp) == 290) {
			System.out.println(pz + "-->" + pp);
		    }
		}
	    }
	    System.out.println(diameterSq2);
	    if (diameterSq2 != diameterSq) {
		
		System.out.println("ERROR");
		return;
		}
	}
    }

    public static double angle(Vector v1, Vector v2) {
	return Math.acos(((v1.x*v2.x)+(v1.y*v2.y)));
    }

    public static long dist(Point p1, Point p2) {
	return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
    }

    public static long ccw(Point x, Point y, Point z) {
	//right = -, left = +, colinear = 0;
	return ((y.x-x.x)*(z.y-y.y)-(y.y-x.y)*(z.x-y.x));
    }
}

class Vector {
    public double x, y;
    public Vector(double x, double y) {
	double mag = Math.sqrt(x*x+y*y);
	if (mag == 0) mag = 1;
	this.x = x/mag;
	this.y = y/mag;
    }
    public String toString() {
	return "(" + x + "," + y + ")";
    }
}

class Point implements Comparable<Point> {
    public long x, y;
    public Point(long x, long y) {
	this.x = x;
	this.y = y;
    }
    public int compareTo(Point p) {
	if (x < p.x) return -1;
	else if (x == p.x) {
	    if (y < p.y) return -1;
	    return 1;
	}
	return 1;
    }
    public boolean equals(Object o) {
	Point pt = (Point) o;
	return pt.x == x && pt.y == y;
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
