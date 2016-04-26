/*
ID: joshkir1
LANG: JAVA
TASK: cryptcow
*/
import java.io.*;
import java.util.*;

	class Test {
		public Test(char[] in) {
			this.in = in;
		}
		public char[] in;
		public int hashCode() {
			return Arrays.hashCode(in);
		}
		public boolean equals(Object o) {
			Test t = (Test) o;
			return Arrays.equals(in, t.in);
		}
	}
class cryptcow
{
	static int hasher(char[] str) // Had to use this. It works better than HashSet<Test> :(
	{
		long h=0,g,i;
		for(i=0;i<str.length;i++)
		{
			h=(h<<4)+((long)str[(int)i]);
			g=h &0xf0000000l; //7 '0's;
			if(g > 0) h^=g>>24;
			h&=~g;
		}
		return ((int)h%131071);
	}
	static Test t = new Test(null);
	static int ans = -1;
	static String goal = "Begin the Escape execution at the Break of Dawn";
	static HashMap<Character, Integer> goalFreq = new HashMap<Character, Integer>();
	static HashSet<Test> sub = new HashSet<Test>();
	static int maxdepth = 0;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("cryptcow.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cryptcow.out")));
		String in = sc.nextLine();
		long m = System.currentTimeMillis();
		/*for (char c : goal.toCharArray()) {
			if (goalFreq.containsKey(c)) {
				goalFreq.put(c, goalFreq.get(c)+1);
			} else {
				goalFreq.put(c, 1);
			}
		}*/
		/*HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
		for (char c : in.toCharArray()) {
			if (freq.containsKey(c)) {
				freq.put(c, freq.get(c)+1);
			} else {
				freq.put(c, 1);
			}
		}*/
		/*for (Map.Entry<Character, Integer> ent : freq.entrySet()) {
			if (ent.getKey() == 'C' || ent.getKey() == 'O' || ent.getKey() == 'W') {
				if (goalFreq.containsKey(ent.getKey()) && ent.getValue() < goalFreq.get(ent.getKey())) {
					out.println("0 0");
					out.close();
					return;
				}
			} else { 
				if (!goalFreq.containsKey(ent.getKey())) {
					out.println("0 0");
					out.close();
					return;
				} else {
					if (ent.getValue() != goalFreq.get(ent.getKey())) {
						out.println("0 0");
						out.close();
						return;
					}
				}
			}
		}*/
		int cs = 0;
		int os = 0;
		int ws = 0;
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) == 'C') cs++;
			else if (in.charAt(i) == 'O') os++;
			else if (in.charAt(i) == 'W') ws++;
		}
		maxdepth = Math.min(Math.min(cs, os), ws);
		for (int i = 0; i < goal.length(); i++) {
			for (int j = i; j < goal.length(); j++) {
				sub.add(new Test(goal.substring(i, j+1).toCharArray()));
			}
		}
		//for (String s : sub) System.out.println(s);
		work(in.toCharArray(), 0);
		if (ans == -1) {
			out.println("0 0");
		} else {
			out.println("1 " + ans);
		}
		System.out.println("TOOL: " + totalMs);
		System.out.println("TOTAL: " + (System.currentTimeMillis() - m));
		out.close();
		System.exit(0);
	}
	static int d;
	static long totalMs;
	//static HashSet<Test> sets = new HashSet<Test>();

	static boolean[] used = new boolean[131071];
	public static boolean good(char[] arr) {
		if (goal.length() != arr.length) return false;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != goal.charAt(i)) return false;
		}
		return true;
	}
	public static char[] substring(char[] in, int i, int j) {
		char[] next = new char[j-i];
		int idx = 0;
		System.arraycopy(in, i, next, 0, j-i);
		/*for (int x = i; x < j; x++) {
			next[idx++] = in[x];
		}*/
		return next;
	}

	public static char[] merge(char[] a1, char[] a2, char[] a3, char[] a4) {
		char[] next = new char[a1.length+a2.length+a3.length+a4.length];
		int idx = 0;
		System.arraycopy(a1, 0, next, 0, a1.length);
		System.arraycopy(a3, 0, next, a1.length, a3.length);
		System.arraycopy(a2, 0, next, a1.length+a3.length, a2.length);
		System.arraycopy(a4, 0, next, a1.length+a3.length+a2.length, a4.length);
		/*for (char c : a1) next[idx++] = c;
		for (char c : a3) next[idx++] = c;
		for (char c : a2) next[idx++] = c;
		for (char c : a4) next[idx++] = c;*/
		return next;
	}
	public static void work(char[] in, int depth) {
		//System.out.println(Arrays.toString(in));
		int h = hasher(in);
		used[h] = true;
		//System.out.println("DEPTH: " + d++);
		//System.out.println("IN: " + in);
		if (in.length < goal.length()) {
			//System.out.println("ret2");
			return;
		}
		if (ans != -1) return;
		if (good(in)) {
			ans = depth;
			//System.out.println("ret3");
			return;
		} else {
			for (int i = 0; i < in.length; i++) {
				if (in[i] == 'O' || in[i] == 'W') return;
				if (in[i] == 'C') {
					break;
				} else {
					if (in[i] != goal.charAt(i)) return;
				}
			}
			int idx = goal.length() - 1;
			for (int i = in.length-1; i >= 0; i--) {
				if (in[i] == 'O' || in[i] == 'C') return;
				if (in[i] == 'W') {
					break;
				} else {
					if (goal.charAt(idx--) != in[i]) return;
				}
			}
			int last = -1;
			for (int i = 0; i < in.length; i++) {
				if (in[i] == 'C' || in[i] == 'O' || in[i] == 'W') {
					if (last != i) {
						char[] subs = substring(in, last+1, i);
						t.in = subs;
						if (subs.length >= 1 && !sub.contains(t)) {
							return;
						}
					}
					last = i;
				}
			}
			ArrayList<Integer>cs = new ArrayList<Integer>(10);
			ArrayList<Integer>os = new ArrayList<Integer>(10);
			ArrayList<Integer>ws = new ArrayList<Integer>(10);
			for (int i = 0; i < in.length; i++) {
				if (in[i] == 'C') cs.add(i);
				else if (in[i] == 'O') os.add(i);
				else if (in[i] == 'W') ws.add(i);
			}
			if (cs.size() != os.size() || os.size() != ws.size() || cs.size() != ws.size())  {
				return;
			}
			//for (int i = 0; i < in.length; i++) {
			for (int j : os) {
				if (in[j] == 'O') {
					//for (int j = i+1; j < in.length; j++) {
					for (int i : cs) {
						if (in[i] == 'C') {
							//for (int k = j + 1; k < in.length; k++) {
							for (int z = ws.size()-1; z >= 0; z--) {
								int k = ws.get(z);
								if (!(j > i && k > j)) continue; 
								if (in[k] == 'W') {
									//Found a series of C O and W.
									//swap the in betwens and remove the C O W.
									long ms = System.currentTimeMillis();

									char[] next = new char[in.length-3];
									System.arraycopy(in, 0, next, 0, i);
									System.arraycopy(in, j+1, next, i, k-(j+1));
									System.arraycopy(in, i+1, next, i+k-(j+1), j-(i+1));
									System.arraycopy(in, k+1, next, i+k-(j+1)+j-(i+1), in.length-(k+1));
									int hash = hasher(next);
									if (used[hash]) continue;
									totalMs += (System.currentTimeMillis() - ms);
									work(next, depth+1);
								}
							}
						}
					}
				}
			}
		}
	}
}