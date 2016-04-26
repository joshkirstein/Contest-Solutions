/*
ID: joshkir1
LANG: JAVA
TASK: combo
*/
import java.io.*;
import java.util.*;

class combo
{
	static int N;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("combo.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));

		N = sc.nextInt();
		int cnt = 0;
		int x = sc.nextInt(), y = sc.nextInt(), z = sc.nextInt();
		int c = sc.nextInt(), b = sc.nextInt(), l = sc.nextInt();
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				for (int k = 1; k <= N; k++) {
					if (y(i, x) && y(j, y) && y(k, z)) {
						cnt++;
					} else if (y(i, c) && y(j, b) && y(k, l)) {
						cnt++;
					}

				}
			}
		}


		out.println(cnt);
		out.close();
		
		System.exit(0);
	}

	public static boolean y(int x, int y) {
		int val1 = (y+2)%N;
		int val2 = (y+1)%N;
		int val3 = (y-1);
		if (val1 == 0) val1 = 1;
		if (val2 == 0) val2 = 1;
		return val1 == x || val2 == x || val3 == x || (y-2) == x || Math.abs(x-y) <= 2 || y == x || (x == N && y == 1) || (x == N && y == 2) || (y == N && x == 2) || (y == N && x == 1) || (y == (N-1) && x == 1) || (y == 1 && x == (N-1));
	}
}