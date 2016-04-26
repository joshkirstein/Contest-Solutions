import java.util.*;

public class A {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(), t = sc.nextInt();
		int[] arr = new int[N-1];
		for (int i = 0; i < N-1; i++) arr[i] = sc.nextInt();
		int cur = 0;
		boolean[] vis = new boolean[N];
		while (!vis[cur]) {
			vis[cur] = true;
			if (cur >= N-1) break;
			cur = cur + arr[cur];
		}
		if (vis[t-1]) System.out.println("YES");
		else System.out.println("NO");
	}
}
