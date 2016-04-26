/*
ID: joshkir1
LANG: JAVA
TASK: skidesign
*/
import java.io.*;
import java.util.*;

class skidesign
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("skidesign.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
		int N = sc.nextInt();
		int[] map = new int[N];
		for (int i = 0; i < N; i++) {
			map[i] = sc.nextInt();
		}
		int cost = Integer.MAX_VALUE;
		for (int i = 0; i <= 100-17; i++) {
			int lo = i;
			int hi = i+17;
			int c = 0;
			for (int j = 0; j < N; j++) {
				int clamp = clamp(map[j], lo, hi);
				c += clamp*clamp;
			}
			cost = Math.min(cost, c);
		}
		out.println(cost);
		out.close();
		System.exit(0);
	}

	public static int clamp(int val, int min, int max) {
		if (val >= min && val <= max) return 0;
		else return Math.min(Math.abs(min-val), Math.abs(max-val));
	}
}