import java.util.*;

public class StringMatching {
	static String s;
	static String sub;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		s = sc.nextLine();
		String best = null;
		for (int i = 1; i <= s.length(); i++) {
			if (s.length() % i != 0) continue;
			ArrayList<String> strs = new ArrayList<String>();
			for (int j = 0; j < s.length(); j++) {
				if (j+i>s.length()) break;
				sub = s.substring(j, j+i);
				strs.add(sub);
				if (works()) {
					//System.out.println("====WORKS");
					if (best == null || sub.compareTo(best) < 0) {
						best = sub;
					}
				} else {
					//System.out.println("====NOGO");
				}
			}
			if (best != null) break;
		}
		System.out.println("" + best);
	}

	static Boolean[][][] memo;
	static boolean works() {
		memo = new Boolean[s.length()][sub.length()][s.length()/sub.length()+1];
		return DP(0,0,s.length()/sub.length());

	}

	static boolean DP(int sIdx, int subIdx, int left) {
		//System.out.println("sIdx: " + sIdx + " subIdx: " + subIdx + " left: " + left);
		if (sIdx > s.length()) return false;
		if (subIdx > sub.length()) return false;
		if (left == 0) {
			//System.out.println("======sIdx: " + sIdx + " subIdx: " + subIdx + " left: " + left);
			if ((subIdx == sub.length() || subIdx == 0)) return true;
			return false;
		}
		if (subIdx == sub.length()) return DP(sIdx, 0, left-1);
		if (sIdx == s.length()) {
			if (subIdx == sub.length() && left == 0) return true;
			return false;
		}
		if (memo[sIdx][subIdx][left] != null) return memo[sIdx][subIdx][left];
		boolean work = false;
		if (sub.charAt(subIdx) == s.charAt(sIdx)) {
			work |= DP(sIdx+1, subIdx+1, left);
		}
		for (int amt = 1; amt < left && !work; amt++) {
			work |= DP(sIdx, 0, amt) && DP(sIdx+amt*sub.length(), subIdx, left-amt);
		}
		return memo[sIdx][subIdx][left] = work;
	}
}