import java.util.*;

public class Resolution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int caze = 0;
		while (T-- > 0) {
			int GP = sc.nextInt(), GC = sc.nextInt(), GF = sc.nextInt();
			int N = sc.nextInt();
			int[] p = new int[N];
			int[] c = new int[N];
			int[] f = new int[N];
			for (int i = 0; i < N; i++) {
				p[i] = sc.nextInt();
				c[i] = sc.nextInt();
				f[i] = sc.nextInt();
			}
			boolean works = false;
			for (int i = 0; i < (1 << N) && !works; i++) {
				int gp = 0, gc = 0, gf = 0;
				for (int j = 0; j < N; j++) {
					if ((i & (1 << j)) != 0) {
						gp += p[j];
						gc += c[j];
						gf += f[j];
					}
				}
				if (gp == GP && gf == GF && gc == GC) {
					works = true;
				}
			}
			if (works) {
				System.out.println("Case #" + (++caze) + ": yes");
			} else {
				System.out.println("Case #" + (++caze) + ": no");
			}
		}
	}
}