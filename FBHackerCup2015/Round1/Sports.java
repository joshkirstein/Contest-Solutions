import java.util.*;

public class Sports {
	static final int MOD = 1_000_000_007;
	static int OUR, THEIR;
	static Integer[][] dp1mem;
	static Integer[][] dp2mem;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		for (int x = 1; x <= cases; x++) {
			String[] caze = sc.next().split("-");;
			int our = Integer.parseInt(caze[0]);
			int their = Integer.parseInt(caze[1]);
			OUR = our;
			THEIR = their;
			dp1mem = new Integer[OUR+1][THEIR+1];
			dp2mem = new Integer[OUR+1][THEIR+1];
			System.out.println("Case #" + x + ": " + dp1(1,0) + " " + dp2(0, 0));
		}
	}

	static int dp2(int score1, int score2) {
		if (dp2mem[score1][score2] != null) return dp2mem[score1][score2];
		if (score1 == OUR && score2 == THEIR) return 1; // done
		if (score1 == score2) {
			if (score2 == THEIR) {
				//we can add to ours
				return dp2mem[score1][score2] = dp2(score1+1, score2);
			} else {
				//only add ot theirs
				return dp2mem[score1][score2] = dp2(score1, score2+1);
			}
		} else if (score1 > score2) {
			return dp2mem[score1][score2] = dp2(score1+1, score2);
		} else  {
			int sum = 0;
			sum += dp2(score1+1, score2)%MOD;//can equal (unlike last)
			if (score2 != THEIR)
				sum += dp2(score1, score2+1)%MOD;
			sum %= MOD;
			return dp2mem[score1][score2] = sum;
		}
	}

	static int dp1(int score1, int score2) {
		if (dp1mem[score1][score2] != null) return dp1mem[score1][score2];
		if (score1 == OUR && score2 == THEIR) return 1; // done
		if (score1 == score2) {
			return dp1mem[score1][score2]=dp1(score1+1, score2);//only 1 possible move...
		} else if (score1 > score2) {
			int sum = 0;
			if (score1 != OUR) sum += dp1(score1+1, score2)%MOD;
			if (score2 != THEIR && score1 > (score2+1)) sum += dp1(score1, score2+1)%MOD;
			sum %= MOD;
			return dp1mem[score1][score2]=sum;
		} else return 0;//shouldn't happen...
	}
}