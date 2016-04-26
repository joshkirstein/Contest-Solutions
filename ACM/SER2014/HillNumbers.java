import java.util.*;
import java.io.*;
public class HillNumbers {
	
	static String hill;
	static Long[][][][] memo;
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		hill = sc.next();
		if (!isHill(hill)) {
			System.out.println(-1);
			return;
		}
		memo = new Long[hill.length()][10][2][2];
		System.out.println(count(0,0,true,true, 0));
	}

	static long count(int dig, int lastDig, boolean rising, boolean equal, int depth) {
		if (dig >= hill.length()) {
			int ans = 0;
			if (lastDig == 0 && rising) ans=0;
			else ans=1;
			return ans;
		}
		if (memo[dig][lastDig][rising?1:0][equal?1:0] != null) 
			return memo[dig][lastDig][rising?1:0][equal?1:0];
		int curDig = hill.charAt(dig)-'0';
		long cnt = 0;
		if (rising) {
			int upper = 9;
			if (equal) upper = curDig;
			for (int i = lastDig; i <= upper; i++) {
				cnt += count(dig+1, i, true, equal && i == curDig, depth+1);
			}
			for (int i = 0; i <= upper; i++) {
				if (i == lastDig) break;
				cnt += count(dig+1, i, false, equal && i == curDig, depth+1);
			}
		} else {
			int upper = lastDig;
			if (equal) upper = lastDig;
			if (equal) upper = Math.min(upper, curDig);
			for (int i = upper; i >= 0; i--) {
				cnt += count(dig+1, i, false, equal && i == curDig, depth+1);
			}
		}
		return memo[dig][lastDig][rising?1:0][equal?1:0]=cnt;
	}

	static boolean isHill(String s) {
		boolean rise = true;
		for (int i = 1; i < s.length(); i++) {
			char prev = s.charAt(i-1);
			char cur = s.charAt(i);
			if (prev < cur) {
				if (!rise) return false;
			}
			if (prev > cur) {
				if (rise) {
					rise = false;
				}
			}
		}
		return true;
	}
}