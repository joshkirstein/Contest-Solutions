/*
ID: joshkir1
LANG: JAVA
TASK: fence9
*/
import java.io.*;
import java.util.*;

class fence9
{
	// uses pick's theorem
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("fence9.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence9.out")));
		
		int N = sc.nextInt(), M = sc.nextInt(), P = sc.nextInt();
		// (0,0) -> (N, M) -> (P, 0) -> (0, 0)
		double area = (((double)(P * M)) / 2.0);
		//Pick's theorem: A=i+b/2-1...i=A-b/2+1...
		double b = numLattice(0, 0, N, M) + numLattice(P, 0, N, M) + numLattice(0, 0, P, 0)-3;
		double i = area - b / 2.0 + 1; // Pick's theorem.
		out.println((int)i);
		out.close();
		System.exit(0);
	}

	public static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a%b);
	}

	//Number of lattice points on a line segment from (x,y)->(x1, y1)
	public static int numLattice(int x, int y, int x1, int y1) {
		int gd = gcd(Math.abs(x1-x), Math.abs(y1-y));
		return gd+1;
	}
}