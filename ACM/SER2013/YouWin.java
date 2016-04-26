import java.util.*;

public class YouWin {
	
	static String word;
	static int[][] memo;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			word = sc.next();
			if (word.equals("0")) break;
			memo = new int[1<<word.length()][word.length()+1];
			for (int[] arr : memo) {
				Arrays.fill(arr, -1);
			}
			System.out.println(dp(0,0)+word.length());
		}
	}

	static int dp(int mask, int pos) {
		if (mask == ((1<<word.length())-1)) return 0;
		if (memo[mask][pos] != -1) return memo[mask][pos];
		char lastChar = 'A';
		int cnt = 0;
		for (int i = 0; i < word.length(); i++) {
			if ((mask & (1<<i)) != 0) {
				cnt++;
				if (cnt == pos) {
					lastChar = word.charAt(i);
				}
			}
		}
		cnt = 0;
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < word.length(); i++) {
			if ((mask & (1<<i)) != 0) {
				cnt++;
			} else {
				//try and place i!
				int moveDist = Math.abs(pos-cnt);
				int chDist = charDist(word.charAt(i), lastChar);
				int cost = moveDist+chDist+dp(mask|(1<<i), cnt+1);
				best = Math.min(best, cost);
			}
		}
		return memo[mask][pos]=best;
	}

	static int charDist(char to, char from) {
		int min = Math.abs(from-to);//to down to from or from down to to
		min = Math.min(min, Math.abs('Z'-from)+1+Math.abs('A'-to));
		min = Math.min(min, Math.abs('Z'-to)+1+Math.abs('A'-from));
		return min;
	}
}