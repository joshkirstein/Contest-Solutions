import java.util.*;

public class Coverage {
	static double EPS = 0.000001;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Pt[] pts = new Pt[N];
		for (int i = 0; i < N; i++) {
			pts[i] = new Pt(sc.nextDouble(),sc.nextDouble());
		}
		int[] comp = new int[N];
		int compIdx = 1;
		for (int i = 0; i < N; i++) {
			if (comp[i] == 0) {
				dfs(i, compIdx, comp, pts);
				compIdx++;
			}
		}





		int count = 2; // 2 (if we have 1 pt, answer is 2)...
		for (int i = 0; i < pts.length; i++) {
			for (int j = i+1; j < pts.length; j++) {
				if (pts[i].dist(pts[j]) > 4+EPS) continue;
				HashSet<Integer> comps1 = new HashSet<Integer>();
				HashSet<Integer> comps2 = new HashSet<Integer>();

				Circle c1 = new Circle(pts[i].x, pts[i].y, 2.0);
				Circle c2 = new Circle(pts[j].x, pts[j].y, 2.0);
				double[][] centers = c1.intersect(c2);
				centers[0][0] += pts[i].x;
				centers[1][0] += pts[i].x;
				centers[0][1] += pts[i].y;
				centers[1][1] += pts[i].y;
				Pt first = new Pt(centers[0][0], centers[0][1]);
				Pt second = new Pt(centers[1][0], centers[1][1]);
				for (int k = 0; k < N; k++) {
					if (pts[k].dist(first) <= 2+EPS) comps1.add(comp[k]);
					if (pts[k].dist(second) <= 2+EPS) comps2.add(comp[k]);
				}


				int cnt1 = 1;
				int cnt2 = 1;
				for (int k = 0; k < pts.length; k++) {
					if (comps1.contains(comp[k])) cnt1++;
					if (comps2.contains(comp[k])) cnt2++;
				}


				count = Math.max(count, cnt1);
				count = Math.max(count, cnt2);
			}
		}
		System.out.println(count);
	}

	static void dfs(int cur, int compIdx, int[] comp, Pt[] pts) {
		comp[cur] = compIdx;
		for (int i = 0; i < pts.length; i++) {
			if (comp[i] == 0 && pts[i].dist(pts[cur]) <= 2+EPS) {
				dfs(i, compIdx, comp, pts);
			}
		}
	}
}
class Pt {
	double x, y;
	public Pt(double x, double y) {
		this.x = x;
		this.y = y;
	}
	double dist(Pt other) {
		return Math.sqrt((x-other.x)*(x-other.x)+(y-other.y)*(y-other.y));
	}
}
class Circle {

	static final double EPS = 1e-8;
	double rad, x0, y0;
	Circle(double x0, double y0, double rad) {
		this.x0 = x0;
		this.y0 = y0;
		this.rad = rad;
	}
	//Make sure line recenters to center of circle!!!!
	//that is, transform each point (x, y) -> (x-xc, y-yc)
	//where xc and yc form the center of this circle.
	double[][] intersect(Line line) {
		double a = line.A, b = line.B, c = line.C, r = rad;

		double x0 = -a * c / (a * a + b * b), y0 = -b * c / (a * a + b * b);
		if (c * c > r * r * (a * a + b * b) + EPS){
			return new double[0][];
		} else if (Math.abs(c * c - r * r * (a * a + b * b)) < EPS) {
			return new double[][] {{x0, y0}};
		} else {
			double d = r * r - c * c / (a * a + b * b);
			double mult = Math.sqrt(d / (a * a + b * b));
			double ax, ay, bx, by;
			ax = x0 + b * mult;
			bx = x0 - b * mult;
			ay = y0 - a * mult;
			by = y0 + a * mult;
			return new double[][] {{ax, ay}, {bx, by}};
		}
	}
	double[][] intersect(Circle circle) {
		double x2 = circle.x0-x0, y2 = circle.y0-y0, r2 = circle.rad;
		if (x0 == x2 && y0 == y2) {
			if (rad == r2) 
				return null;//infinite
			else 
				return new double[0][];//none
		}
		Line line = new Line(-2*x2, -2*y2, x2*x2+y2*y2+rad*rad-r2*r2);
		return this.intersect(line);
	}
}
class Line {

	//Ax + By + C = 0
	double A, B, C;
	double x0, y0, x1, y1;
	Line(double x0, double y0, double x1, double y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		A = y1 - y0;
        B = x0 - x1;
        C = -A * x0 - B * y0;
	}
	Line(double A, double B, double C) {
		this.A = A;
		this.B = B;
		this.C = C;
	}
	double[][] intersect(Line line) {
		double A1 = A, B1 = B, C1 = -C;
		double A2 = line.A, B2 = line.B, C2 = -line.C;
		double det = A1*B2 - A2*B1;
	    if(det == 0.0){
	    	return null;//infinite
	    } else {
	        double x = (B2*C1 - B1*C2)/det;
	        double y = (A1*C2 - A2*C1)/det;
	        return new double[][] { {x, y} };
	    }
	}
}