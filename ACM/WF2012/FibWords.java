import java.util.*;

public class FibWords {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long ms = System.currentTimeMillis();
		recurse(101);
		//System.out.println("TOOK: " + (end-ms) + " ms.");
		//for (int i = 0; i < 25; i++) System.out.println("SIZE: " + last[i].length());
		int caze = 1;
		while (sc.hasNextLine()) {
			int N = Integer.parseInt(sc.nextLine());
			pattern = sc.nextLine();
			ans = new long[102];
			Arrays.fill(ans, -1);
			kmp = new KMP(pattern);
			dfs(N);

			System.out.println("Case " + (caze++) + ": " + ans[N]);
		}
		long end = System.currentTimeMillis();
		//System.out.println("TOOK: " + (end-ms) + " ms.");
	}

	static KMP kmp;
	static String pattern;
	static long[] ans;
	static long dfs(int N) {
		if (ans[N] != -1) return ans[N];
		if (N == 0) {
			if (pattern.equals("0")) ans[N] = 1;
			else ans[N] = 0;
			return ans[N];
		}
		if (N == 1) {
			if (pattern.equals("1")) ans[N] = 1;
			else ans[N] = 0;
			return ans[N];
		}
		long comp = dfs(N-1) + dfs(N-2);
		String left = last[N-1];
		String right = first[N-2];
		String total = left+right;
		int ls = left.length();
		int rs = right.length();
		long amt = 0;
		ArrayList<Integer> match = kmp.match(total);
		for (Integer idx : match) {
			if (idx+pattern.length()-1 >= ls && idx < ls) amt++;
		}
		comp += amt;
		//System.out.println("N: " + N);
		//System.out.println(left + "     " + right + " AMT: " + amt);
		return (ans[N] = comp);
	}

	static String[] last = new String[102];
	static String[] first = new String[102];

	static void recurse(int n) {
		if (last[n] != null) return;
		last[n] = "";
		first[n] = "";
		if (n == 0) {
			last[n] = "0";
			first[n] = "0";
			return;
		}
		if (n == 1) {
			last[n] = "1";
			first[n] = "1";
			return;
		}
		recurse(n-1);
		recurse(n-2);
		String n1l = last[n-1];
		String n1f = first[n-1];
		String n2l = last[n-2];
		String n2f = first[n-2];
		//make last:
		String totall = n1l + n2l;
		String totalf = n1f + n2f;
		if ((n1l.length() + n2l.length()) <= 100000) {
			last[n] = n1l + n2l;
		} else {
			last[n] = totall.substring(totall.length()-100000, totall.length());
		}
		if ((n1f.length() + n2f.length()) <= 100000) {
			first[n] = n1f+n2f;
		} else {
			first[n] = totalf.substring(0, 100000);
		}
	}
}
class KMP {
	
	String pattern;
	int[] fail;
	KMP(String pattern) {
		fail = new int[pattern.length()];
		this.pattern = pattern;
		fail[0] = -1;
		if (pattern.length() > 1) {
			fail[1] = 0;
			int i = 2;
			int m = 0;
			while (i != fail.length){
				if (pattern.charAt(m) == pattern.charAt(i-1)){
					m++;
					fail[i] = m;
					i++;
				} else if (fail[m] != -1){
					m = fail[m];
				} else {
					fail[i] = 0;
					m = 0;
					i++;
				}
			}
		}
	}

	ArrayList<Integer> match(String text) {
		int m = 0;
		int i = 0;
		ArrayList<Integer> matches = new ArrayList<Integer>();
		if (pattern.length() > text.length()) return matches;
		/*MAIN:
		for (int i = 0; i <= text.length()-pattern.length(); i++) {
			for (int j = 0; j < pattern.length(); j++) {
				if (text.charAt(i+j) != pattern.charAt(j)) continue MAIN;
			}
			matches.add(i);
		}*/
		while (m + i < text.length()) {
			if (text.charAt(m + i) == pattern.charAt(i)) {
				i++;
				if(i == pattern.length()){
					matches.add(m);
					i = 0;
					m++;
				}
			} else if (i == 0) {
				m++;
			} else {
				m = m + i - fail[i];
				i = fail[i];
			}
		}
 		return matches;
	}
}