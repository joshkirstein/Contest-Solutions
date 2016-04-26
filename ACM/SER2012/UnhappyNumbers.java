import java.util.*;

public class UnhappyNumbers {

	static boolean[] ishappy = new boolean[2001];

	static {
		vis = new boolean[2001];
		ishappy[0] = true;//we dont want to count 0 as unhappy
		//0 is also not happy. but this will allow it to not be counted as unhappy
		for (int i = 1; i <= 2000; i++) {
			ishappy[i] = check(i);
		}
	}

	static boolean[] vis;

	public static boolean check(int happy) {
		if (happy == 1) return true;
		if (ishappy[happy]) return true;
		if (vis[happy]) return false;
		vis[happy] = true;
		int sum = 0;
		String s = String.valueOf(happy);
		for (char c : s.toCharArray()) sum += (c-'0')*(c-'0');
		return ishappy[happy]=check(sum);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			long from = sc.nextLong();
			long to = sc.nextLong();
			if (from == 0l && to == 0l) return;
			System.out.println(count(to)-count(from-1));
		}
	}

	static String upt;

	static long count(long upto) {
		if (upto == 0) return 0;
		upt = String.valueOf(upto);
		long cnt = dfs(0, 0);
		for (int i = 0; i < upt.length(); i++) 
			cnt += countUnhappy(0, i, true);
		return cnt;
	}

	static long dfs(int idx, int sqsum) {
		if (idx >= upt.length()) return ishappy[sqsum] ? 0l : 1l;
		long count = 0;
		int min = 0;
		if (idx == 0) min = 1;
		int max = (upt.charAt(idx)-'0');
		for (int i = min; i < max; i++) {
			count += countUnhappy(sqsum+i*i, upt.length()-(idx+1), false);
		}
		count += dfs(idx+1, sqsum+max*max);
		return count;
	}

	static long[][][] memo;
	static {
		memo = new long[2001][19][2];
		for (long[][] arr : memo) {
			for (long[] a : arr) {
				Arrays.fill(a, -1l);
			}
		}
	}

	//count the number of unhappy numbers with digleft digits..
	//start=is digleft start?
	static long countUnhappy(int digsum, int digleft, boolean start) {
		if (digleft == 0 && !ishappy[digsum]) return 1l;
		if (digleft < 0) return 0;
		if (memo[digsum][digleft][start?1:0] != -1) 
			return memo[digsum][digleft][start?1:0];
		int min = 0;
		if (start) min = 1;

		long count = 0;
		for (int i = min; i <= 9; i++) {
			count += countUnhappy(digsum+i*i, digleft-1, false);
		}
		return memo[digsum][digleft][start?1:0]=count;
	}
}