import java.util.*;

public class C {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Point start = new Point(sc.nextInt(), sc.nextInt());
		Point end = new Point(sc.nextInt(), sc.nextInt());
		int n = sc.nextInt();
		Line[] lines = new Line[n];
		for (int i = 0; i < n; i++) {
			lines[i] = new Line(sc.nextInt(), sc.nextInt(), sc.nextInt());
		}
		int[] sData = getData(start, lines);
		int[] eData = getData(end, lines);
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			if (sData[i] != eData[i]) cnt++;
		}
		System.out.println(cnt);
	}

	static int[] getData(Point p, Line[] lines) {
		int[] data = new int[lines.length];
		for (int i = 0; i < lines.length; i++) {
			data[i] = lines[i].side(p);
		}
		return data;
	}
}
class Line {
	double a;
	double b;
	double c;
	public Line(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public int side(Point p) {
		return sgn(a*p.x+b*p.y+c);
	}
	public int sgn(double d) {
		if (d < 0) return -1;
		else return 1;
	}
}
class Point {
	double x, y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
}