/*
ID: joshkir1
LANG: JAVA
TASK: buylow
*/
import java.io.*;
import java.util.*;
import java.math.*;
class buylow
{
	public static void main(String[] args) throws Exception
	{
		Parser sc = new Parser(new FileInputStream("buylow.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("buylow.out")));

		N = sc.nextInt();
		arr = new int[N];

		dp = new int[N];
		adj = new ArrayList[N];
		min = new int[N];
		for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
		dp[N-1] = 1;
		int max = 1;
		BigInteger[] dpcount = new BigInteger[N];
		Arrays.fill(dpcount, BigInteger.ZERO);
		for (int i = N-2; i >= 0; i--) {
			for (int j = i+1; j < N; j++) {
				if (arr[i] > arr[j]) {
					dp[i] = Math.max(dp[i], dp[j]+1);
				}
			}
			if (dp[i] == 0) dp[i] = 1;
			max = Math.max(max, dp[i]);
		}
		int[] seen = new int[1000000];
		for (int i = N-1; i >= 0; i--) {
			//HashSet<Integer> seen = new HashSet<Integer>();
			boolean done = false;
			for (int j = i+1; j < N; j++) {
				if (seen[arr[j]] != i && arr[i] > arr[j]) {
					seen[arr[j]] = i;
					if (dp[j]+1==dp[i]) {
						done = true;
						dpcount[i] = dpcount[i].add(dpcount[j]);
					}
				}
			}
			if (!done) dpcount[i] = BigInteger.ONE;
		}
		//System.out.println(Arrays.toString(dpcount));
		//HashSet<Integer> seen = new HashSet<Integer>();
		//System.out.println(Arrays.toString(dp));
		int hold = N+1;
		for (int i = 0; i < N; i++) {
			if (seen[arr[i]] != hold) {
				seen[arr[i]] = hold;

				if (dp[i] == max) {
					cnt = cnt.add(dpcount[i]);
				}
			}
		}


		System.out.println(max + " " + cnt);
		out.println(max + " " + cnt);
		out.close();
		System.exit(0);
	}
	static ArrayList[] adj;
	static int[] dp;
	static int[] min;
	static int[] arr;
	static int N;
	static int maxx;
	static BigInteger cnt = BigInteger.ZERO;
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