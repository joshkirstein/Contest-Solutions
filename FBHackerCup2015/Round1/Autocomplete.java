import java.util.*;
import java.io.*;
public class Autocomplete {
	
	public static void main(String[] args) throws Exception {
		Parser sc = new Parser(System.in);
		int T = sc.nextInt();
		int x = 1;
		while(T-->0) {
			int N = sc.nextInt();
			String[] words = new String[N];
			for (int i = 0; i < N; i++) words[i] = sc.next();
			TreeSet<String> dic = new TreeSet<String>();
			int cnt = 0;
			for (String s : words) {
				String succ = dic.floor(s);
				String succ2 = dic.ceiling(s);
				if (succ == null && succ2 == null) {
					cnt++;
					//System.out.println(s + " TOOK 1");
					dic.add(s);
				} else {
					int mix = 0;
					Integer t1 = null;
					Integer t2 = null;
					if (succ != null) {
						int t = 0;
						int min = Math.min(s.length(), succ.length());
						for (int i = 0; i < min; i++) {
							if (s.charAt(i) != succ.charAt(i)) break;
							t++;
						}
						t++;
						if (succ.contains(s)) t--;
						mix = Math.max(mix, t);
						t1 = t;
					}
					if (succ2 != null) {
						int t = 0;
						int min = Math.min(s.length(), succ2.length());
						for (int i = 0; i < min; i++) {
							if (s.charAt(i) != succ2.charAt(i)) break;
							t++;
						}
						t++;
						if (succ2.contains(s)) t--;
						mix = Math.max(mix, t);
						t2 = t;
					}
					dic.add(s);
					cnt += mix;
					//System.out.println(s + " TOOK: " + t1 + "(" + succ + ")" + t2 + "(" + succ2 + ")");
				}


			}
			System.out.println("Case #" + (x++) + ": " + cnt);
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