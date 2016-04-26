import java.util.*;

public class Gears {
	static LinkedList[] adj;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		Circle[] circles = new Circle[N];
		adj = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			circles[i] = new Circle(sc.nextInt(), sc.nextInt(), sc.nextInt());
			adj[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < N; i++) {
			Circle c1 = circles[i];
			for (int j = i+1; j < N; j++) {
				Circle c2 = circles[j];
				int sqdist = (c1.x-c2.x)*(c1.x-c2.x) + (c1.y-c2.y)*(c1.y-c2.y);
				int sumsq = (c1.rad+c2.rad)*(c1.rad+c2.rad);
				if (sqdist == sumsq) {
					//connected
					adj[i].add(j);
					adj[j].add(i);
				}
			}
		}
		int start = 0;
		int end = N-1;
		Queue<Integer> bfs = new LinkedList<Integer>();
		int[] vis = new int[N];
		int[] par = new int[N];
		Arrays.fill(vis, -1);
		vis[0] = 1;
		par[0] = -1;
		bfs.add(0);
		while (!bfs.isEmpty()) {
			int pop = bfs.poll();
			LinkedList<Integer> tooo = (LinkedList<Integer>) adj[pop];
			for (Integer to : tooo) {
				if (par[pop] != to) {
					if (vis[to] != -1) {
						if ((vis[to] % 2) == (vis[pop] % 2)) {
							//Cycle
							System.out.println("-1");
							return;
						}
					} else {
						vis[to] = vis[pop]+1;
						par[to] = pop;
						bfs.add(to);
					}
				}
			}
		}
		if (vis[N-1] == -1) {
			System.out.println("0");
			return;
		} else {
			boolean badTurn = (vis[0]%2)!= (vis[N-1]%2);
			int a = circles[N-1].rad;
			int b = circles[0].rad;
			int gd = gcd(a,b);
			if (gd != 0) {
				a /= gd;
				b /= gd;
			}
			if (badTurn) b = -b;
			System.out.println(a + " " + b);
		}
	}
	static int gcd(int a, int b) {
		if (a == 0) return b;
		if (b == 0) return a;
		return gcd(b, a%b);
	}
}
class Fraction {
	public int a, b;
}
class Circle {
	public int x, y, rad;
	public Circle(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
	}
}
