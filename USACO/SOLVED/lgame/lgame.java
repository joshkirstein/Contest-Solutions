/*
ID: joshkir1
LANG: JAVA
TASK: lgame
*/
import java.io.*;
import java.util.*;
import java.math.*;
class lgame
{
	static HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(new FileInputStream("lgame.in"));
		BufferedReader br = new BufferedReader(new FileReader("lgame.dict"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lgame.out")));
		TNode root = new TNode();
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.equals(".")) break;
			addword(root, line);
		}
		String get = sc.nextLine();
		output = new TreeSet<Pair>();
		best = 0;
		chars = get;
		dfs(root, 0, 0, "");
		int max = (1 << chars.length());
		TreeSet<Pair> outputHold = output;
		output = new TreeSet<Pair>();
		int HOLD = best;
		best = 0;
		for (int i = 1; i < max; i++) {

			//use exactly mask
			int mask = i;
			//use some from notmask?
			int notmask = ~i;

			dfs(root, 0, mask, "");
			TreeSet<Pair> outHold = output;
			int bestHold = best;
			output = new TreeSet<Pair>();
			best = 0;
			dfs(root, 0, notmask, "");

			if (bestHold+best>HOLD) {
				HOLD = bestHold+best;
				outputHold = new TreeSet<Pair>();
				for (Pair s1 : outHold) {
					for (Pair s2 : output) {
						if (s1.compareTo(s2) > 0) continue;
						outputHold.add(new Pair(s1.w1, s2.w1));
					}
				}
			} else if (bestHold+best == HOLD) {
				for (Pair s1 : outHold) {
					for (Pair s2 : output) {
						if (s1.compareTo(s2) > 0) continue;
						outputHold.add(new Pair(s1.w1, s2.w1));
					}
				}
			}
			best = 0;
			output = new TreeSet<Pair>();
		}



		out.println(HOLD);
		for (Pair p : outputHold) out.println(p);

		out.close();
		System.exit(0);
	}


	static TreeSet<Pair> output;
	static int best;
	static String chars;

	static void dfs(TNode cur, int cost, int mask, String cs) {
		for (int i = 0; i < chars.length(); i++) {
			if ((mask & (1 << i)) == 0) {
				char c = chars.charAt(i);
				if (cur.to[c-'a'] != null) {
					dfs(cur.to[c-'a'], cost+getCost(c), mask | (1 << i), cs + c);
				}
			}
		}
		if (cur.word) {
			if (cost > best) {
				best = cost;
				output = new TreeSet<Pair>();
				output.add(new Pair(cs, null));
			} else if (cost == best) {
				output.add(new Pair(cs, null));
			}
		}
	}

	static class Pair implements Comparable<Pair> {
		String w1, w2;
		public Pair(String w1, String w2) {
			this.w1 = w1;
			this.w2 = w2;
		}
		public String toString() {
			return w1 + "" + (w2 == null ? "" : (" " + w2));
		}
		public boolean equals(Object o) {
			Pair p = (Pair) o;
			if (w2 == null && p.w2 != null) return false;
			if (p.w2 == null && w2 != null) return false;
			if (w2 == null && p.w2 != null) return w1.equals(p.w1);
			return p.w1.equals(w1) && p.w2.equals(w2);
		}
		public int compareTo(Pair p) {
			if (w1.compareTo(p.w1) == 0) {
				if (w2 == null && p.w2 == null) return 0;
				if (w2 == null) return -1;
				if (p.w2 == null) return 1;
				return w2.compareTo(p.w2);
			} else return w1.compareTo(p.w1);
		}
	}

	static HashSet<String> used = new HashSet<String>();
	static int getCost(char c) {
		switch (c) {
			case 'q': return 7;
			case 'w': return 6;
			case 'e': return 1;
			case 'r': return 2;
			case 't': return 2;
			case 'y': return 5;
			case 'u': return 4;
			case 'i': return 1;
			case 'o': return 3;
			case 'p': return 5;
			case 'a': return 2;
			case 's': return 1;
			case 'd': return 4;
			case 'f': return 6;
			case 'g': return 5;
			case 'h': return 5;
			case 'j': return 7;
			case 'k': return 6;
			case 'l': return 3;
			case 'z': return 7;
			case 'x': return 7;
			case 'c': return 4;
			case 'v': return 6;
			case 'b': return 5;
			case 'n': return 2;
			case 'm': return 5;
		}
		return 0;
	}

	static void addword(TNode root, String word) {
		TNode cur = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			int idx = c-'a';
			if (cur.to[idx] == null) {
				cur.to[idx] = new TNode();
			}
			cur = cur.to[idx];
		}
		cur.word = true;
	}
}
class TNode {
	TNode[] to = new TNode[('z'-'a')+1];
	boolean word = false;
}