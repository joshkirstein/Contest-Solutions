import java.util.*;

public class Gears {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Gear[] gears = new Gear[N];
		for (int i = 0; i < N; i++) {
			gears[i] = new Gear(sc.nextInt(), sc.nextInt(), sc.nextInt());
		}
		//1 = clockwise
		//2 = counterclockwise
		int[] direction = new int[N];
		direction[0] = 1;
		Queue<Integer> bfsq = new LinkedList<Integer>();
		bfsq.add(0);
		while (!bfsq.isEmpty()) {
			int pop = bfsq.poll();
			for (int i = 0; i < N; i++) {
				if (i == pop) continue;
				if (gears[pop].withinDist(gears[i])) {
					if (direction[i] == 0) {
						//direction hasnt been set yet
						direction[i] = (3-direction[pop]);
						bfsq.add(i);
					} else {
						if (direction[i] == direction[pop]) {
							//we've got a problem
							System.out.println("-1");
							return;
						}
					}
				}
			}
		}
		if (direction[N-1] == 0) {
			System.out.println(0);
		} else {
			int mult = 1;
			if (direction[0] != direction[N-1]) mult = -1;
			int gcd = gcd(gears[0].rad, gears[N-1].rad);
			System.out.println((gears[N-1].rad/gcd) + " " + (mult*(gears[0].rad/gcd)));
		}
	}

	static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a%b);
	}
}
class Gear {
	int x, y, rad;
	Gear(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
	}
	boolean withinDist(Gear other) {
		double dist = Math.hypot(x-other.x, y-other.y);
		return dist <= rad+other.rad;
	}
}