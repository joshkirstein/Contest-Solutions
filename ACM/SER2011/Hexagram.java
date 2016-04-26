import java.util.*;

public class Hexagram {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			vals = new int[12];
			sum = 0;
			for (int i = 0; i < 12; i++) {
				vals[i] = sc.nextInt();
				sum += vals[i];
			}
			if (vals[0] == 0 && vals[1] == 0) break;
			counter = 0;
			if (sum % 3 != 0) {
				System.out.println(0);
				continue;
			}
			dfs(0, new boolean[12], new int[12]);
			System.out.println(counter/12);// (div 2 for reflection) (div 6 for rotations)
		}
	}
	static int sum;
	static int[] vals;
	static int counter = 0;

	static void dfs(int cur, boolean[] used, int[] assign) {
		if (cur == 12) {
			counter++;
			return;
		}
		for (int i = 0; i < used.length; i++) {
			if (!used[i]) {
				used[i] = true;
				assign[cur] = vals[i];
				boolean go = true;
				if (cur == 3) {
					int goalSum = assign[0]+assign[1]+assign[2]+assign[3];
					if (goalSum != sum / 3) go=false;
				}
				if (cur == 6) {
					int goalSum = assign[0]+assign[1]+assign[2]+assign[3];
					int madeSum = assign[3]+assign[4]+assign[5]+assign[6];
					if (goalSum != madeSum) go=false;
				}
				if (cur == 8) {
					int goalSum = assign[0]+assign[1]+assign[2]+assign[3];
					int madeSum = assign[0]+assign[8]+assign[7]+assign[6];
					if (goalSum != madeSum) go=false;
				}
				if (cur == 10) {
					int goalSum = assign[0]+assign[1]+assign[2]+assign[3];
					int madeSum = assign[9]+assign[2]+assign[4]+assign[10];
					if (goalSum != madeSum) go=false;
				}
				if (cur == 11) {
					int goalSum = assign[0]+assign[1]+assign[2]+assign[3];
					int madeSum = assign[10]+assign[5]+assign[7]+assign[11];
					int madeSum2 = assign[11]+assign[8]+assign[1]+assign[9];
					if (goalSum != madeSum) go=false;
					if (goalSum != madeSum2) go=false;
				}

				if (go) dfs(cur+1, used, assign);
				used[i] = false;
				assign[cur] = 0;
			}
		}
	}
}