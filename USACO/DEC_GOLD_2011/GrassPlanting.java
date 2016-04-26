import java.util.*;
import java.io.*;
//heavy light
//HLD
//decomposition
public class GrassPlanting {
	static int N, M;
	static LinkedList[] adj;
	static int[] cnt;
	static int[] lev;
	static int[] parent;
	public static void main(String[] args) throws Exception {
		Parser sc = new Parser(System.in);
		PrintStream pw = System.out;
		N = sc.nextInt();
		M = sc.nextInt();
		adj = new LinkedList[N];
		cnt = new int[N];
		lev = new int[N];
		parent = new int[N];
		for (int i = 0; i < N; i++) adj[i] = new LinkedList<Pair>();
		for (int i = 0; i < N-1; i++) {
			int u = sc.nextInt()-1, v = sc.nextInt()-1;
			Pair p1 = new Pair(v);
			Pair p2 = new Pair(u);
			p1.twin = p2;
			p2.twin = p1;
			adj[u].add(p1);
			adj[v].add(p2);
		}
		lev[0] = 1;
		parent[0] = -1;
		dfs1(0);
		dfs2(0, null, 0);
		for (int i = 0; i < M; i++) {
			String type = sc.next();
			int u = sc.nextInt()-1;
			int v = sc.nextInt()-1;
			if (type.equals("P")) {
				if (lev[u] > lev[v]) {
					int temp = v;
					v = u;
					u = temp;
				}
				//System.out.println("LCA: " + lca(u, v));
				if (lca(u, v) == 0) {
					//go up to root for each!
					add(u, 0);
					add(v, 0);
				} else {
					//follow v to u...
					add(u, lca(u, v));
					add(v, lca(u, v));
				}
			} else if (type.equals("Q")) {
				if (lev[u] > lev[v]) {
					int temp = v;
					v = u;
					u = temp;
				}
				int sum = sum(v);
				System.out.println((sum));
			}
		}
	}
	//from above to
	static Pair get(int from, int to) {
		Pair edge = null;
		for (Object adjacent : adj[from]) {
			Pair pair = (Pair) adjacent;
			int adj = pair.to;
			if (adj == to) {
				edge = pair;
				break;
			}
		}
		return edge;
	}

	static void add(int cur, int stop) {
		if (cur == stop) return;
		int parent = GrassPlanting.parent[cur];
		Pair edge = get(parent, cur);
		if (edge.heavy) {
			int top = edge.top;
			if (lev[top] < lev[stop]) {
				//somewhere in the middle!
				Pair last = null;


				for (Object adjacent : adj[stop]) {
					Pair pair = (Pair) adjacent;
					if (pair.heavy) {
						last = pair;
						break;//has to be one and only one!
					}
				}


				last.bit.incr(last.ID, edge.ID);
				return;
			} else {
				edge.bit.incr(1, edge.ID);
				add(top, stop);
			}
		} else {
			edge.grassCount++;
			add(parent, stop);
		}
	}

	static int sum(int x) {

		int par = parent[x];
		Pair edge = get(par, x);
		if (edge.heavy) {
			return edge.bit.query(edge.ID);
		} else {
			return edge.grassCount;
		}
	}


	static int lca(int u, int v) {
		int curx = u;
		int cury = v;
		while (true) {
			if (curx == cury) return curx;
			if (lev[curx] > lev[cury]) {
				curx=parent[curx];
			} else if (lev[curx] < lev[cury]) {
				cury=parent[cury];
			} else {
				curx=parent[curx];
				cury=parent[cury];
			}
		}
	}

	static void dfs1(int root) {
		cnt[root] = 1;
		for (Object adjacent : adj[root]) {
			Pair pair = (Pair) adjacent;
			int adj = pair.to;
			if (lev[adj] == 0) {
				lev[adj] = lev[root]+1;
				parent[adj] = root;
				dfs1(adj);
				cnt[root] += cnt[adj];
			}
		}
	}

	static void dfs2(int root, Pair prev, int count) {
		int div = cnt[root]/2;
		boolean hadHeavy = false;
		for (Object adjacent : adj[root]) {
			Pair pair = (Pair) adjacent;
			int adj = pair.to;
			if (lev[adj] > lev[root]) {
				if (cnt[adj] > div) {
					pair.heavy = true;
					if (prev != null) {
						pair.top = prev.top;
						pair.prevEdge = prev;
					} else {
						pair.top = root;
					}
					dfs2(adj, pair, count+1);
					hadHeavy = true;
				} else {
					dfs2(adj, null, 0);
				}
			}
		}
		if (!hadHeavy && prev != null) {
			int curId = count;
			Pair cur = prev;
			ST blt = new ST(count+1);
			cur.bit = blt;
			cur.ID = curId;
			while (true) {
				curId--;
				cur = cur.prevEdge;
				if (cur == null) break;
				cur.bit = blt;
				cur.ID = curId;
			}
		}
	}
}
//BIT
class ST {
	int[] tree;
	public ST(int size) {
		this.tree = new int[size];
	}
	int lsone(int x) {return x&-x;}

	void incr(int from, int to) {
		add(from, 1);
		add(to+1, -1);
		return;
	}

	void add(int x, int val) {
		while (x < tree.length) {
			tree[x] += val;
			x += lsone(x);
		}
	}

	int query(int x) {
		int sum = 0;
		while ( x > 0) {
			sum += tree[x];
			x-=lsone(x);
		}
		return sum;
	}
}
class Pair {
	boolean heavy;
	Pair prevEdge;
	ST bit;
	int top;
	int ID;
	boolean hasGrass;
	int to;
	Pair twin;

	int grassCount = 0;
	public Pair(int to) {
		this.to = to;
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