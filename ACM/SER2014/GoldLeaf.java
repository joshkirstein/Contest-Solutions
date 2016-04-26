import java.util.*;
import java.io.*;
public class GoldLeaf {
	
	public static void main(String[] args) throws Exception {
		//File dir = new File("/Users/joshkirstein/Downloads/goldleaf/in/");
		//File[] files = dir.listFiles();
		//for (File ff : files) {
		Scanner sc = new Scanner(System.in/*new FileInputStream(ff)*/);
		//while (sc.hasNextLine()) System.out.println(sc.nextLine());
		int rows = sc.nextInt();
		int cols = sc.nextInt();
		char[][] grid = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			String row = sc.next();
			for (int j = 0; j < cols; j++) {
				grid[i][j] = row.charAt(j);
			}
		}
		Line line = null;
		//horizontal:
		for (int i = 0; i < rows-1; i++) {
			//cut horizontal between i-1 and i
			Line test = new Line(i, 0, i, cols-1, 0);
			if (test.okay(grid)) line = max(line, test);
		}
		//vertical:
		//cut vertical between cols i-1 and i
		for (int i = 0; i < cols-1; i++) {
			Line test = new Line(0, i, rows-1, i, 1);
			if (test.okay(grid)) line = max(line, test);
		}
		//NS diagonal:
		for (int i = 0; i < cols; i++) {
			Line test = new Line(Math.min(i, rows-1), i-Math.min(i, rows-1), 0, i, 2);
			if (test.okay(grid)) line = max(line, test);
			Line test2 = new Line(rows-1, i, rows-1-Math.min(rows-1, (cols-1)-i), i+Math.min(rows-1, (cols-1)-i), 2);
			if (test2.okay(grid)) line = max(line, test2);
		}
		for (int i = 0; i < rows; i++) {
			Line test = new Line(i, 0, i-Math.min(cols-1, i), Math.min(cols-1, i), 2);
			if (test.okay(grid)) line = max(line, test);
			Line test2 = new Line(i,0,i+Math.min(cols-1, rows-1-i),Math.min(cols-1, rows-1-i),3);
			//System.out.println("test2: " + test2);
			if (test2.okay(grid)) line = max(line, test2);
			Line test3 = new Line(i+Math.min(rows-1-i, cols-1), cols-1-Math.min(rows-1-i, cols-1), i, cols-1, 2);
			if (test3.okay(grid)) line = max(line, test3);
			Line test4 = new Line(i-Math.min(i, cols-1),cols-1-Math.min(i, cols-1),i,cols-1,3);
			if (test4.okay(grid)) line = max(line, test4);
		}
		//SN diagonal:
		for (int i = 0; i < cols; i++) {
			Line test = new Line(0, i, Math.min(rows-1, (cols-1)-i), i+Math.min(rows-1, (cols-1)-i), 3);
			//System.out.println("TESTING: " + test);
			if (test.okay(grid)) line = max(line, test);
			Line test2 = new Line(rows-1-Math.min(rows-1, i), i-Math.min(rows-1, i), rows-1, i, 3);
			if (test2.okay(grid)) line = max(line, test2);
		}
		System.out.println(line);
		//}
	}

	static Line max(Line cur, Line next) {
		if (cur == null) return next;
		if (next.compareTo(cur) < 0) return next;
		return cur;
	}
}
class Line implements Comparable<Line> {
	int type;
	int r1, c1, r2, c2;
	public Line(int r1, int c1, int r2, int c2, int type) {
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
		this.type = type;
	}
	public String toString() {
		return (r1+1) + " " + (c1+1) + " " + (r2+1) + " " + (c2+1);
	}
	public boolean okay(char[][] grid) {
		if (type == 0) return type0(grid); //horizontal
		if (type == 1) return type1(grid); //vertical
		if (type == 2) return type2(grid); //NS diagonal
		if (type == 3) return type3(grid); //SN diagonal
		return false;
	}
	public boolean type0(char[][] grid) {
		int cols = grid[0].length;
		int rows = grid.length;
		int minMove = Math.min(rows-1-r1, r1+1);
		for (int i = 0; i < minMove; i++) {
			int row1 = r1-i;
			int row2 = r1+i+1;
			for (int j = 0; j < cols; j++) {
				char test1 = grid[row1][j];
				char test2 = grid[row2][j];
				if (test1 == test2) return false;
			}
		}
		int lowest = r1-minMove;
		int highest = r1+minMove;
		for (int i = 0; i < rows; i++) {
			if (i < lowest || i > highest) {
				for (int j = 0; j < cols; j++) {
					char test = grid[i][j];
					if (test != '#') return false;
				}
			}
		}
		return true;
	}	
	public boolean type1(char[][] grid) {
		int cols = grid[0].length;
		int rows = grid.length;
		int minMove = Math.min(cols-1-c1, c1+1);
		for (int i = 0; i < minMove; i++) {
			int col1 = c1-i;
			int col2 = c1+i+1;
			for (int j = 0; j < rows; j++) {
				char test1 = grid[j][col1];
				char test2 = grid[j][col2];
				if (test1 == test2) return false;
			}
		}
		int lowest = c1-minMove;
		int highest = c1+minMove;
		for (int i = 0; i < cols; i++) {
			if (i < lowest || i > highest) {
				for (int j = 0; j < rows; j++) {
					char test = grid[j][i];
					if (test != '#') return false;
				}
			}
		}
		return true;
	}
	public boolean type2(char[][] grid) {
		int cols = grid[0].length;
		int rows = grid.length;
		int currow = r1;
		int curcol = c1;
		if (grid[currow][curcol] != '#') return false;
		while (currow != r2 || curcol != c2) {
			if (grid[currow][curcol] != '#') return false;
			currow--;
			curcol++;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i >= r2 && i <= r1 && j >= c1 && j <= c2) {
					int ci = i;
					int cj = j;
					int moves = 0;
					while (((r2-ci)+(c2-cj)) > 0) {
						ci++;
						cj++;
						moves++;
					}
					if (moves == 0) continue;
					if ((r2-ci)+(c2-cj) < 0) moves--;
					for (int k = 0; k < moves; k++) {
						ci++;
						cj++;
					}
					if (grid[i][j] == grid[ci][cj]) {
						return false;
					}
				} else {
					if (grid[i][j] != '#') {
						return false;
					}
				}
			}
		}
		return true;
	}
	public boolean type3(char[][] grid) {		
		int cols = grid[0].length;
		int rows = grid.length;
		int currow = r1;
		int curcol = c1;
		if (grid[currow][curcol] != '#') return false;
		while (currow != r2 || curcol != c2) {
			if (grid[currow][curcol] != '#') return false;
			currow++;
			curcol++;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i >= r1 && i <= r2 && j >= c1 && j <= c2) {
					int ci = i;
					int cj = j;
					int moves = 0;
					while ((c2-cj) > (r2-ci)) {
						ci--;
						cj++;
						moves++;
					}
					if (moves == 0) continue;
					if ((c2-cj) < (r2-ci)) moves--;
					for (int k = 0; k < moves; k++) {
						ci--;
						cj++;
					}
					if (grid[i][j] == grid[ci][cj]) {
						return false;
					}
				} else {
					if (grid[i][j] != '#') {
						return false;
					}
				}
			}
		}
		return true;
	}
	public int compareTo(Line l) {
		if (r1 == l.r1) {
			if (c1 == l.c1) {
				if (r2 == l.r2) {
					if (c2 == l.c2) {
						return 0;
					}
					return c2-l.c2;
				}
				return r2-l.r2;
			}
			return c1-l.c1;
		}
		return r1 - l.r1;
	}
}