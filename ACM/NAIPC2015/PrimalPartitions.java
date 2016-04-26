import java.io.*;
import java.util.*;
public class PrimalPartitions {	
	static int[] best = new int[1000001];
	static boolean[] isNotPrime = new boolean[1500];
	static {
		long ms = System.currentTimeMillis();
		isNotPrime[0] = true;
		isNotPrime[1] = true;
		for (int i = 2; i < isNotPrime.length; i++) {
			if (isNotPrime[i]) continue;
			int prime = i;
			int temp = prime;
			while (temp < best.length) {
				best[temp] = Math.max(best[temp], prime);
				temp += prime;
			}
			for (int j = 2; j*i < isNotPrime.length; j++) {
				isNotPrime[i*j] = true;
			}
		}
		for (int i = 2; i < best.length; i++) {
			if (best[i] == 0) best[i] = i;
		}
		debug("TOOK " + (System.currentTimeMillis()-ms) + " ms.");
	}
	static boolean DEBUG = true;
	static void debug(String s) {
		if (!DEBUG) return;
		System.out.println(s);
	}
	static int[] arr;
	public static void main(String[] args) throws Exception {
		Parser sc = new Parser(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		arr = new int[N];
		for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
		int[][][] memo = new int[N][K][];

		for (int pLeft = 0; pLeft < K; pLeft++) {
			for (int idx = 0; idx < N; idx++) {
				if (idx == 0) {
					if (pLeft == 0) {
						memo[idx][pLeft]=new int[]{best[arr[idx]], arr[idx]};
					} else {
						memo[idx][pLeft]=new int[0];
					}
				} else if (pLeft == 0) {
					int[] retval = memo[idx-1][pLeft];
					if (retval.length != 0) {
						int gcd = gcd(arr[idx], retval[1]);
						memo[idx][pLeft]=new int[] {best[gcd], gcd};
					}
				} else {			
					int[] partHere = memo[idx-1][pLeft-1];
					int[] dont = memo[idx-1][pLeft];
					int[] bes = new int[0];
					if (partHere.length > 0) {
						bes = new int[] {Math.min(partHere[0],best[arr[idx]]), arr[idx]};
					}
					if (dont.length > 0) {
						int gcd = gcd(dont[1], arr[idx]);
						int[] test = new int[] {Math.min(dont[0], best[gcd]), gcd};
						if (bes.length == 0 || (bes[0] < test[0]) || (bes[0] == test[0] && best[bes[1]] < best[test[1]])) {
							bes = test;
						}
					}
					memo[idx][pLeft]=bes;
				}
			}
		}
		System.out.println(memo[N-1][K-1][0]);
	}

	static int gcd(int a, int b) {
		if (b == 0) return a;
		if (a == 0) return b;
		return gcd(b, a%b);
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