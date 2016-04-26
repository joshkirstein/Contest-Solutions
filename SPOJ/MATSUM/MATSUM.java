import java.util.*;

public class MATSUM {
	
	public static void main(String[] args) throws Exception {
		Parser sc = new Parser(System.in);
		int T = sc.nextInt();
		int N;
		String type;
		StringBuilder br = new StringBuilder("");
		bit = new int[1025][1025];
		while (T-->0) {
			N = sc.nextInt();
			n = N;
			m = N;
			for (int i = 0; i < N; i++) {
				Arrays.fill(bit[i], 0, N+2, 0);
			}
			while (true) {
				type = sc.next();
				if (type.contains("END")) break;
				if (type.equals("SET")) {
					int x = sc.nextInt();
					int y = sc.nextInt();
					int num = sc.nextInt();
					update(x+1, y+1, num);
				} else if (type.equals("SUM")) {
					int x1 = sc.nextInt();
					int y1 = sc.nextInt();
					int x2 = sc.nextInt();
					int y2 = sc.nextInt();
					br.append(query(x1+1, y1+1, x2+1, y2+1) + "\n");
				}
			}
			br.append("\n");
		}
		System.out.print(br.toString());
		System.exit(0);
	}

	static int[][] bit;
	static int n, m;

	static int query(int x1, int y1, int x2, int y2) {
		return query(x2, y2) - query(x1-1, y1) - query(x1, y1-1) + query(x1-1, y1-1);
	}

	static int query(int x, int y) {
		if (x <= 0 || y <= 0) return 0;
		int sum = 0;
		int tempy = y;
		while (x > 0) {
			y = tempy;
			while (y > 0) {
				sum += bit[x][y];
				y -= (y&-y);
			}
			x -= (x&-x);
		}
		return sum;
	}

	static int value(int x, int y) {
		int sum = bit[x][y];
		int tempy = y;
		int xx = x - (x & -x);
		x--;
		while (x != xx) {
			y = tempy;
			int yy = y - (y & -y);
			y--;
			while (y != yy) {
				sum -= bit[xx][yy];
				yy -= (yy & -yy);
			}
			xx -= (xx & -xx);
		}
		return sum;
	}

	static void update(int x, int y, int val) {
		val = val - value(x, y);
		int tempy = y;
		while (x <= n) {
			y = tempy;
			while (y <= m) {
				bit[x][y] += val;
				y += (y&-y);
			}
			x += (x&-x);
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