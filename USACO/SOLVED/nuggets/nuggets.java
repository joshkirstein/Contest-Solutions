/*
ID: joshkir1
LANG: JAVA
TASK: nuggets
*/
import java.io.*;
import java.util.*;

class nuggets
{
	//if gcd of all numbers isn't 1,
	//there's either going to be unbounded max or every number is possible.
	//if at least 256 consecutive numbers are doable, everything is doable.
	//The exact number of consecutive numbers that needs to be done is the minimum
	//package
	//Apparently max is no bigger than 65k.
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("nuggets.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nuggets.out")));
		int N = sc.nextInt();
		int[] nuggets = new int[N];
		for (int i = 0; i < N; i++) {
			nuggets[i] = sc.nextInt();
		}
		int g = 1;
		for (int i = 0; i < N; i++) {
			if (i == 0) {
				if (N != 1) {
					g = gcd(nuggets[0], nuggets[1]);
					i++;
				} else {
					if (nuggets[i] == 1) {
						g = 1;
					} else {
						g = -1;
					}
				}
			} else {
				g = gcd(g, nuggets[i]);
			}
		}
		if (g != 1) {
			out.println("0");
			out.close();
			System.exit(0);
		}
		boolean[] cando = new boolean[66000];
		cando[0] = true;
		int max = 0;
		for (int i = 0; i < cando.length; i++) {
			if (!cando[i]) {
				max = i;
			} else {
				for (int nug : nuggets) {
					if (i+nug<66000)
						cando[i+nug] = true;
				}
			}
		}
		out.println(max);
		out.close();
		System.exit(0);
	}
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a%b);
	}
}