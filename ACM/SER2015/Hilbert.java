import java.util.*;

public class Hilbert {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int s = sc.nextInt();
		ArrayList<Pt> pts = new ArrayList<Pt>();
		for (int i = 0; i < N; i++) {
			pts.add(new Pt(sc.nextInt(),sc.nextInt()));
		}
		pts = sort(pts, new int[]{0,1,2,3}, 0, 0, s, s);
		for (Pt p : pts) {
			System.out.println(p.x + " " + p.y);
		}
	}

	static ArrayList<Pt> sort(ArrayList<Pt> pts, int[] orient, double lx, double ly, double ux, double uy) {
		if (pts.size() == 0) return pts;
		if (pts.size() == 1) return pts;
		//split into four lists, sort each, and combine
		//based on orient \in [0,3]
		ArrayList<Pt> q1 = new ArrayList<Pt>();
		ArrayList<Pt> q2 = new ArrayList<Pt>();
		ArrayList<Pt> q3 = new ArrayList<Pt>();
		ArrayList<Pt> q4 = new ArrayList<Pt>();
		double midx = (lx+ux)/2;
		double midy = (ly+uy)/2;
		//System.out.println("MIDX: " + midx);
		//lowerleft of q2 is (lx, midy)
		//upperright of q2 is (midx, uy)

		//lowerleft of q4 is (midx, ly)
		//upperleft of q4 is (ux, midy)
		for (Pt p : pts) {
			int cnt = 0;
			if (p.x >= lx && p.y >= ly && p.x <= midx && p.y <= midy) {
				q1.add(p);
				cnt++;
			}
			if (p.x >= lx && p.y >= midy && p.x <= midx && p.y <= uy) {
				q2.add(p);
				cnt++;
			}
			if (p.x >= midx && p.y >= midy && p.x <= ux && p.y <= uy) {
				q3.add(p);
				cnt++;
			}
			if (p.x >= midx && p.y >= ly && p.x <= ux && p.y <= midy) {
				q4.add(p);
				cnt++;
			}
			if (cnt != 1) throw new RuntimeException();
		}
		int[] q1o = new int[] { orient[0], orient[3], orient[2], orient[1] };
		int[] q2o = new int[] { orient[0], orient[1], orient[2], orient[3] };
		int[] q3o = new int[] { orient[0], orient[1], orient[2], orient[3] };
		int[] q4o = new int[] { orient[2], orient[1], orient[0], orient[3] };

		int[][] orients = new int[4][4];
		orients[0] = q1o;
		orients[1] = q2o;
		orients[2] = q3o;
		orients[3] = q4o;
		int[] idx = new int[4];
		for (int i = 0; i < 4; i++) {
			idx[orient[i]] = i;
		}
		q1 = sort(q1, orients[idx[0]], lx, ly, midx, midy);
		q2 = sort(q2, orients[idx[1]], lx, midy, midx, uy);
		q3 = sort(q3, orients[idx[2]], midx, midy, ux, uy);
		q4 = sort(q4, orients[idx[3]], midx, ly, ux, midy);

		ArrayList<Pt> answer = new ArrayList<Pt>();
		ArrayList[] llls = new ArrayList[4];
		llls[0] = q1;
		llls[1] = q2;
		llls[2] = q3;
		llls[3] = q4;
		answer.addAll(llls[orient[0]]);
		answer.addAll(llls[orient[1]]);
		answer.addAll(llls[orient[2]]);
		answer.addAll(llls[orient[3]]);


		return answer;
	}
}
class Pt {
	public Pt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x,y;
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}