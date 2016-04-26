/*
ID: joshkir1
LANG: JAVA
TASK: fence4
*/
import java.io.*;
import java.util.*;

class fence4
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("fence4.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence4.out")));
		ArrayList<Segment> segments = new ArrayList<Segment>();
		int corners = sc.nextInt();
		int xStart, yStart;
		int[] x = new int[corners];
		int[] y = new int[corners];
		int ox = sc.nextInt();
		int oy = sc.nextInt();
		for (int i = 0; i < corners; i++) {
			int xx = sc.nextInt(), yy = sc.nextInt();
			x[i] = xx;
			y[i] = yy;
		}
		for (int i = 0; i < corners-1; i++) {
			int x1 = x[i], y1 = y[i];
			int x2 = x[(i+1)], y2=y[(i+1)];
			segments.add(new Segment(x1, y1, x2, y2, i, i+1));
		}
		segments.add(new Segment(x[0], y[0], x[corners-1], y[corners-1], 0, corners-1));//input specs....first point in input must come first.
		for (int i = 0; i < segments.size(); i++) {
			for (int j = 0; j < segments.size(); j++) {
				if (i != j) {
					Segment s1 = segments.get(i);
					Segment s2 = segments.get(j);
					if (s1.intersectsOK(s2)) { // OK doesn't care about intersects which lie on ends of segments..
						System.out.println("NOFENCE (" + s1 + ") -> (" + s2 + ")");
						System.exit(0);
					}
				}
			}
		}
		ArrayList<Segment> segs = new ArrayList<Segment>();
		for (int i = 0; i < segments.size(); i++) {
			Segment seg = segments.get(i);
			//These swaps are necessary because of the way we format the last line segment. instead of (x[i], y[i], x[i+1], y[i+1]) for the
			//last segment we do (x[0], y[0], x[i], y[i]) because the output wants it that way...
			//thee code below needs it to be in the former structure to work, so we gotta swap and unswap..
			if (i == segments.size() -1) {
				//swap
				double tempx = seg.x;
				double tempy = seg.y;
				seg.x = seg.x1;
				seg.y = seg.y1;
				seg.x1 = tempx;
				seg.y1 = tempy;
			}
			Segment next = segments.get((i+1)%segments.size());
			if (next == segments.get(segments.size() - 1)) {
				double tempx = next.x;
				double tempy = next.y;
				next.x = next.x1;
				next.y = next.y1;
				next.x1 = tempx;
				next.y1 = tempy;
			}
			int prevIdx = (i-1);
			if (prevIdx < 0) prevIdx = segments.size()-1;
			Segment prev = segments.get(prevIdx);

			if (prev == segments.get(segments.size() - 1)) {
				double tempx = prev.x;
				double tempy = prev.y;
				prev.x = prev.x1;
				prev.y = prev.y1;
				prev.x1 = tempx;
				prev.y1 = tempy;
			}
			Segment midSeg = new Segment(ox, oy, (seg.x+seg.x1)/2.0, (seg.y+seg.y1)/2.0);
			boolean visibleEZ = true;
			for (int j = 0; j < segments.size(); j++) {
				if (i != j) {
					if (midSeg.intersectsCheck(segments.get(j), prev, next)) {
						//if this intersects and endpoint and is still visible, **i think** the ray stuff handles it.
						//Not sure, but got AC...
						visibleEZ = false;
						break;
					}
				}
			}
			if (!segs.contains(seg) && visibleEZ) {
				segs.add(seg);
				System.out.println("GOT: " + seg + " FROM GOOD");
			}
			//draw ray from (ox, oy) through each endpoint. check the segment that each intersects first.
			//use an endpoint only if the edges connected to at that point don't cross it
			Ray ray1 = new Ray(ox, oy, seg.x, seg.y);
			Ray ray2 = new Ray(ox, oy, seg.x1, seg.y1);
			Segment closest1 = null;
			double dist1 = Double.MAX_VALUE;
			Segment closest2 = null;
			double dist2 = Double.MAX_VALUE;
			boolean goodOne = false;
			boolean goodTwo = false;
			Point pt2 = null;
			//one or two is good if seg[i] and seg[i+1] are on the same side of each respective ray.
			double cross1Ours = Segment.cross(seg.x-ox, seg.y-oy, prev.x-ox, prev.y-oy);
			double cross1Next = Segment.cross(seg.x-ox, seg.y-oy, seg.x1-ox, seg.y1-oy);
			boolean cross1Pos = cross1Ours > 0;
			boolean cross1NPos = cross1Next > 0;
			goodOne = ((cross1Pos && cross1NPos) || (cross1Ours < 0 && cross1Next < 0));//Checks if crossover from prev to seg...they must be on 'same side' of the ray.
			double cross2Ours = Segment.cross(seg.x1-ox, seg.y1-oy, seg.x-seg.x1, seg.y-seg.y1);
			double cross2Next = Segment.cross(seg.x1-ox, seg.y1-oy, next.x1-seg.x1, next.y1-seg.y1);
			int count1L = 0, count1R = 0;
			int count2L = 0, count2R = 0;
			goodTwo = ((cross2Ours > 0 && cross2Next > 0) || ((cross2Ours < 0 && cross2Next < 0)));//Checks if crossover from seg to next...they must be on 'same side' of the ray.
			if (goodOne || goodTwo) {
				for (int j = 0; j < segments.size(); j++) {
					if (i != j) {
						if (goodOne && j != (i-1)) {
							Point intersect = ray1.intersect(segments.get(j), segments.get(j == 0 ? segments.size() - 1 : j-1), segments.get((j+1)%segments.size()));//find point of intersection
								if (intersect != null) {
								if (intersect.L) {//This is when we intersect at an endpoint. if we intersect at two endpoints that end up 'crossing over', we then they cancel eachother out.
									count1L++;
								}
								if (intersect.R) {
									count1R++;
								}
								double dist = distance(intersect, new Point(ox, oy));
								if (dist < dist1) {
									dist1 = dist;
									closest1 = segments.get(j);
								}
							}
						}
						if (goodTwo && j != (i+1)) {
							Point intersect = ray2.intersect(segments.get(j), segments.get(j == 0 ? segments.size() - 1 : j-1), segments.get((j+1)%segments.size()));
								if (intersect != null) {
								if (intersect.L) {
									count2L++;
								}
								if (intersect.R) {
									count2R++;
								}
								double dist = distance(intersect, new Point(ox, oy));
								if (dist < dist2) {
									dist2 = dist;
									closest2 = segments.get(j);
									pt2 = intersect;
								}
							}
						}
					}
				}
			}
			if (closest1 != null && !segs.contains(closest1) && (count1R == 0 || count1L == 0)) {
				//String s = segs.contains(closest1) ? "****" : "";
				//if (!s.equals("****")) 
					segs.add(closest1);

				System.out.println("DIST1: " + dist1 + " FROM RAY " + ray1 + " TO " + closest1 + " " + " CROSS1OURS: " + cross1Ours + " CROSS1NEXT: " + cross1Next);
			}
			if (closest2 != null && !segs.contains(closest2) && (count2R == 0 || count2L == 0)) {
				//String s = segs.contains(closest2) ? "****" : "";
				//if (!s.equals("****"))
					segs.add(closest2);
				System.out.println("DIST2: " + dist2 + " FROM RAY2 " + ray2 + " TO " + closest2 + " " + "CROSS2OURS: " + cross2Ours + " CROSS2NEXT: " + cross2Next + " AT " + pt2);
				//System.out.println("PREV: " + prev + " NEXT: " + next + " CUR: " + seg);
			}

			//Unswaps the swaps at the beginning (these 3 conditionals)
			if (i == segments.size() - 1) {
				//unswap
				double tempx = seg.x;
				double tempy = seg.y;
				seg.x = seg.x1;
				seg.y = seg.y1;
				seg.x1 = tempx;
				seg.y1 = tempy;
			}

			if (next == segments.get(segments.size() - 1)) {
				double tempx = next.x;
				double tempy = next.y;
				next.x = next.x1;
				next.y = next.y1;
				next.x1 = tempx;
				next.y1 = tempy;
			}
			if (prev == segments.get(segments.size() - 1)) {
				double tempx = prev.x;
				double tempy = prev.y;
				prev.x = prev.x1;
				prev.y = prev.y1;
				prev.x1 = tempx;
				prev.y1 = tempy;
			}
		}
		Collections.sort(segs);//output sepecification
		out.println(segs.size());
		for (Segment seg : segs) {
			out.println(seg);
		}
		out.close();
		System.exit(0);
	}

	public static double distance(Point one, Point two) {
		return Math.pow(one.x-two.x, 2) + Math.pow(one.y-two.y, 2);
	}
}
class Point {
	public double x, y;
	public boolean L, R;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
class Ray extends Segment {
	public Ray(double x, double y, double x1, double y1) {
		super(x, y, x1, y1);
	}
	public static double distance(Point one, Point two) {
		return Math.pow(one.x-two.x, 2) + Math.pow(one.y-two.y, 2);
	}
	public Point intersect(Segment other, Segment prev, Segment next) {
		double x2 = x1;
		double y2 = y1;
		double x1 = x;
		double y1 = y;
		double x3 = other.x;
		double y3 = other.y;
		double x4 = other.x1;
		double y4 = other.y1;
		double denom = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
		if (denom == 0) return null;
		double xnum = (x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4);
		double ynum = (x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4);
		double xpt = xnum/denom;
		double ypt = ynum/denom;
		//Make sure it's ON segment other...
		Point ret = new Point(xpt, ypt);
		if (Math.abs((
			distance(ret, new Point(x3, y3))+distance(ret, new Point(x4, y4))
			))-
			(
				distance(new Point(x3, y3), new Point(x4, y4))
				) > 0.00001) {// makes sure it's on the line segment...
			return null;
		}
		if (distance(ret, new Point(x3, y3)) < 0.00000000001) {
			//use prev
			double first = Segment.cross(x2-x1, y2-y1, prev.x-x2, prev.y-y2);
			double second = Segment.cross(x2-x1, y2-y1, x4-x2, y4-y2);
			if ((first > 0 && second > 0) || (first < 0 && second < 0)) {

				return null;
			} else {
				ret.L = true;
			}
		}
		if (distance(ret, new Point(x4, y4)) < 0.00000000001) {
			double first = Segment.cross(x2-x1, y2-y1, next.x1-x2, next.y1-y2);
			double second = Segment.cross(x2-x1, y2-y1, x3-x2, y3-y2);
			if ((first > 0 && second > 0) || (first < 0 && second < 0)) {
				
				return null;
			} else {
				ret.R = true;
			}
		}
		if (x2 > x1) if (ret.x < x1) return null;
		if (x2 < x1) if (ret.x > x1) return null;
		if (y2 > y1) if (ret.y < y1) return null;//these four conditions makes sure it's in the direction of the ray.
		if (y2 < y1) if (ret.y > y1) return null;
		return ret;
	}
}
class Segment implements Comparable<Segment> {

	public double x, y, x1, y1;

	public boolean equals(Object o) {

		if (o instanceof Segment) {
			Segment so = (Segment) o;
			return x==so.x&&x1==so.x1&&y==so.y&&y1==so.y1;
		}
		return false;
	}

	public Segment(double x, double y, double x1, double y1) {
		this.x =x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}

	public Segment(double x, double y, double x1, double y1, int idx1, int idx2) {
		this.x =x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.idx1 = idx1;
		this.idx2 = idx2;
	}

	public int idx1, idx2;

	public int compareTo(Segment other) {
		if (idx2 == other.idx2) {
			return idx1-other.idx1;
		}
		return idx2-other.idx2;
	}
	public static double cross(double x, double y, double x1, double y1) {
		return x*y1-y*x1;
	}

	public String toString() {
		return ((int)x) + " " + ((int)y) + " " + ((int)x1) + " " + ((int)y1);
	}
	public boolean intersectsOK(Segment other) { // doesn't include endpoints.
		return this.intersects2OK(other) && other.intersects2OK(this);
	}

	private boolean intersects2OK(Segment other) {
		double cross1 = cross(x1-x, y1-y, other.x-x1, other.y-y1);
		double cross2 = cross(x1-x, y1-y, other.x1-x1, other.y1-y1);
		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x: " + other.x + " other.y: " + other.y + " CROSS: " + cross1);
		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x1: " + other.x1 + " other.y1: " + other.y1 + " CROSS: " + cross2);
		boolean pos1 = cross1 > 0.0D;
		boolean pos2 = cross2 > 0.0D;
		//System.out.println(pos1 != pos2);
		return (pos1 != pos2) && Math.abs(cross1) > 0.1 && Math.abs(cross2) > 0.1;
	}

	private boolean intersects2(Segment other) {
		double cross1 = cross(x1-x, y1-y, other.x-x1, other.y-y1);
		double cross2 = cross(x1-x, y1-y, other.x1-x1, other.y1-y1);
		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x: " + other.x + " other.y: " + other.y + " CROSS: " + cross1);

		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x1: " + other.x1 + " other.y1: " + other.y1 + " CROSS: " + cross2);
		boolean pos1 = cross1 > 0.0D;
		boolean pos2 = cross2 > 0.0D;
		//System.out.println(pos1 != pos2);
		return (pos1 != pos2) || cross1 == 0 || cross2 == 0;
	}

	public boolean intersects(Segment other) {// includes endpoints.
		return this.intersects2(other) && other.intersects2(this);
	}

	public boolean intersectsCheck(Segment other, Segment prev, Segment next) {// includes endpoints being on line. prev and next are not used i dont want to remove them.
		return this.intersects2O(other, prev, next) && other.intersects2O(this, prev, next);
	}

	private boolean intersects2O(Segment other, Segment prev, Segment next) {
		double cross1 = cross(x1-x, y1-y, other.x-x1, other.y-y1);
		double cross2 = cross(x1-x, y1-y, other.x1-x1, other.y1-y1);
		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x: " + other.x + " other.y: " + other.y + " CROSS: " + cross1);

		//System.out.println("X: " + x + " Y: " + y + " X1: " + x1 + " Y1: " + y1 + " other.x1: " + other.x1 + " other.y1: " + other.y1 + " CROSS: " + cross2);
		boolean pos1 = cross1 > 0.0D;
		boolean pos2 = cross2 > 0.0D;
		//System.out.println(pos1 != pos2);
		return (pos1 != pos2) || Math.abs(cross1) < 0.001 || Math.abs(cross2) < 0.001;
	}
}