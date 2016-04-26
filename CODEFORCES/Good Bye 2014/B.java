import java.util.*;

public class B {
	static int[] arr;
	static boolean[][] adj;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		arr = new int[N];
		for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
		adj = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			String word = sc.next();
			for (int j = 0; j < N; j++) {
				adj[i][j] = word.charAt(j) == '1';
			}
		}
		for (int i = 0; i < N; i++) {
			boolean[] t = new boolean[N];
			t[i] = true;
			int best = dfs(i, i, t);
			//System.out.println("BEST: " + best + " arr[best]: " + arr[best] + " VIS: " + Arrays.toString(t));
			int temp = arr[best];
			arr[best] = arr[i];
			arr[i] = temp;
		}

		System.out.print(arr[0]);
		for (int i = 1; i < N; i++) {
			System.out.print(" " + arr[i]);
		}
		System.out.println();
	}

	static int dfs(int cur, int after, boolean[] visited) {
		int min = -1;
		for (int j = 0; j < arr.length; j++) {
			if (!visited[j] && (adj[j][cur] || adj[cur][j])) {
				visited[j] = true;
				int idx = dfs(j, after, visited);
				if (idx != -1 && idx >= after) {
					if (min == -1) min = idx;
					else if (arr[min] > arr[idx]) min = idx;
				}
				//System.out.println("IDX: " + idx + " AFTER: " + after + " CUR: " + cur);
			}
		}
		if (cur >= after) {
			if (min == -1) min = cur;
			else if (arr[min] > arr[cur]) min = cur;
		}
		return min;
	}
}