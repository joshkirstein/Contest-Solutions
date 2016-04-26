import java.util.*;

class D {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int row = in.nextInt();
		int col = in.nextInt();
		int count = 0;
		char[][] grid = new char[row][col];
		for (int i = 0; i < row; i++) {
			String line = in.next();
			for (int j = 0; j < col; j++) {
				grid[i][j] = line.charAt(j);
				if (grid[i][j] == '.') count++;
			}
		}
		
		Group best = null;
		
		// row cut
		for (int i = 0; i < row - 1; i++) {
			if (i < row - 1 - i) {
				boolean flag = true;
				//System.out.println("i == " + i + " --> 1");
		  LOOP: for (int j = 0; j <= i; j++) {
					for (int c = 0; c < col; c++) {
					//System.out.println("comparing -- {" + (i-j) + ", " + c + "} with {" + (i+j) + ", " + c + "}");
						if (grid[i - j][c] == grid[i + j+1][c]) {
							flag = false;
							break LOOP;
						}
					}	
				}
				if (flag) {
					Group g = new Group(i + 1, 1, i + 1, col);
					if (best == null) best = g;
					else if (g.compareTo(best) < 0) best = g;
					break;
				}
			} else {
			//System.out.println("i == " + i + " --> 2");
				boolean flag = true;
		  LOOP: for (int j = 0; j < row - 1 - i; j++) {
					for (int c = 0; c < col; c++) {
						if (grid[i - j][c] == grid[i + j+1][c]) {
							flag = false;
							break LOOP;
						}
					}
				}
				if (flag) {
					Group g = new Group(i + 1, 1, i + 1, col);
					if (best == null) best = g;
					else if (g.compareTo(best) < 0) best = g;
					break;
				}
			}
		}
		
		// row cut
		for (int i = 0; i < col - 1; i++) {
			if (i < col - 1 - i) {
				boolean flag = true;
				//System.out.println("i == " + i + " --> 1");
		  LOOP: for (int j = 0; j <= i; j++) {
					for (int c = 0; c < row; c++) {
					//System.out.println("comparing -- {" + (i-j) + ", " + c + "} with {" + (i+j) + ", " + c + "}");
						if (grid[c][i - j] == grid[c][i + j+1]) {
							flag = false;
							break LOOP;
						}
					}	
				}
				if (flag) {
					Group g = new Group(1, i + 1, row, i + 1);
					if (best == null) best = g;
					else if (g.compareTo(best) < 0) best = g;
					break;
				}
			} else {
			//System.out.println("i == " + i + " --> 2");
				boolean flag = true;
		  LOOP: for (int j = 0; j < col - 1 - i; j++) {
					for (int c = 0; c < row; c++) {
						if (grid[c][i - j] == grid[c][i + j+1]) {
							flag = false;
							break LOOP;
						}
					}
				}
				if (flag) {
					Group g = new Group(1, i + 1, row, i + 1);
					if (best == null) best = g;
					else if (g.compareTo(best) < 0) best = g;
					break;
				}
			}
		}
		
		for (int i = 1; i < row + col - 2; i++) {
			if (i < col) {
				int cnt = 1;	
				int area = (i+1) * i / 2;
				boolean flag = true;
		  LOOP: for (int j = i-1; j >= 0; j--) {
					for (int k = 0; k < j; k++) {
						if (grid[j][k] == grid[j+cnt][k+cnt]) {
							flag = false;
							break LOOP;
						}	
					}	
					cnt+= 2; 
				}	
				
				if (flag && area >= count) {
					Group g = new Group(i+1, 1, 1, i+1);
					if (best == null) best = g;
					else if (g.compareTo(best) < 0) best = g;
				}
 			} else {
 				
 			}
		}
		
		System.out.println(best);
		
		
		
	}
}

class Group implements Comparable<Group> {
	int rs, cs, rf, cf;
	
	public Group(int rs, int cs, int rf, int cf) {
		this.rs = rs;
		this.cs = cs;
		this.rf = rf;
		this.cf = cf;
	}
	
	public int compareTo(Group g) {
		if (this.rs == g.rs) {
			if (this.cs == g.cs) {
				if (this.rf == g.rf) {
					return this.cf - g.cf;
				}
				return this.rf - g.rf;
			}
			return this.cs - g.cs;
		}
		return this.rs - g.rs;
	}
	
	public String toString() {
		return rs + " " + cs + " " + rf + " " + cf;
	}
}
