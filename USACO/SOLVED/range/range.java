/*
ID: joshkir1
LANG: JAVA
TASK: range
*/
import java.io.*;
import java.util.*;

class range
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("range.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		int miles = Integer.parseInt(in.readLine());
		int[][] field = new int[miles][miles];
		for (int i = 0; i < miles; i++) {
			String sd = in.readLine();
			for (int j = 0; j < miles; j++) {
				if (sd.charAt(j) == '0') field[i][j] = 0;
				else field[i][j] = 1;
			}
		}
		int[][] dp = new int[miles][miles];
		for (int i = 0; i < miles; i++) {

			for (int j = 0; j < miles; j++) {

				if (field[i][j] == 1) {

					int left = getValue(i, j-1, dp);
					int up = getValue(i-1, j, dp);
					int upleft = getValue(i-1, j-1, dp);
					int min = Math.min(left, Math.min(up, upleft));
					dp[i][j] = min + 1;

				}
			}
		}

		int[] hold = new int[miles+1];
		for (int i = 0; i < miles; i++) {

			for (int j = 0; j < miles; j++) {

				if (field[i][j] == 1) {

					int boxes = dp[i][j];
					
					for (int k = 2; k <= boxes; k++) {
						hold[k]++;
					}
				}
			}
		}

		for (int i = 2; i < hold.length; i++) {
			if (hold[i] > 0) {
				out.append(i + " " + hold[i] + "\n");
			}
		}
		out.close();
		System.exit(0);
	}

	public static int getValue(int i, int j, int[][] array) {
		if (i >= 0 && i < array.length && j >= 0 && j < array.length) {
			return array[i][j];
		}
		return 0;
	}
}