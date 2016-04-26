import java.util.*;
import java.math.*;
public class Zamboni {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int R = sc.nextInt();
		int C = sc.nextInt();
		int curX = sc.nextInt()-1;
		int curY = sc.nextInt()-1;
		long steps = sc.nextLong();
		//int z = 0;
		int rz = R;
		int cz = C;
		char[][] grid = new char[rz][cz];
		for (int i = 0; i < rz; i++)
			Arrays.fill(grid[i], '.');
		int[] dx = {-1,0,1,0};
		int[] dy = {0,1,0,-1};
		long stepSize = 1;
		int dir = 0;
		char curColor = 'A';
		//1+5
		//4+6
		//3+7
		//4+8
		///////
		//up = upDelta*(1+5+9+13+17+...)
		//right = rightDelta*(2+6+10+14+...)
		//arithemtic sum = 0.5*(lastTerm+firstTerm)*numTerms
		//arithmetic term = startTerm+x*delta
		//1+step*delta
		long mod1 = 0;
		long mod2 = 0;
		long mod3 = 0;
		if (steps%4l == 3l) {
			mod1 = 1;
			mod2 = 1;
			mod3 = 1;
		} else if (steps%4l == 2l) {
			mod1 = 1;
			mod2 = 1;
		} else if (steps%4l == 1l) {
			mod1 = 1;
		}
		//steps--;
		//1+5
		//2+6
		//3+7
		//4
		if (steps > 10000) {
			steps -= 10000;
			long lastUp = 1l+4l*(steps/4l+mod1-1);
			long lastRight = 2l+4l*(steps/4l+mod2-1);
			long lastDown = 3l+4l*(steps/4l+mod3-1);
			long lastLeft = 4l+4l*(steps/4l-1);
			lastUp %= rz;
			lastDown %= rz;
			lastRight %= cz;
			lastLeft %= cz;
			//System.out.println("LASTUP: " + lastUp + " LASTRIGHT: " + lastRight + " LASDOWN: " + lastDown + " LASTLEFT: " + lastLeft);
			lastUp = Math.max(lastUp, 0);
			lastRight = Math.max(lastRight, 0);
			lastDown = Math.max(lastDown, 0);
			lastLeft = Math.max(lastLeft, 0);
			long sumUp = ((lastUp+1)%rz)*(((steps/4l+mod1))%rz)/2;
			long sumRight = ((lastRight+2))%cz*(((steps/4l+mod2))%cz)/2;
			long sumDown = ((lastDown+3)%rz)*(((steps/4l+mod3))%rz)/2;
			long sumLeft = ((lastLeft+4)%cz)*((steps/4l)%cz)/2;
			//System.out.println("SUMUP: " + sumUp + " SUMRIGHT: " + sumRight + " SUMDOWN: " + sumDown + " SUMLEFT: " + sumLeft);
			
			sumUp = Math.max(sumUp, 0);
			sumRight = Math.max(sumRight, 0);
			sumDown = Math.max(sumDown, 0);
			sumLeft = Math.max(sumLeft, 0);
			long dr = ((steps+1)/2);
			if ((((steps+1)/2) % 2) != 0) {
				dr = -dr;
			}
			long endX = curX+dr;
			endX %= rz;
			endX += rz;
			endX %= rz;

			long dc = (steps)/2;
			if (steps%2 != 0) dc++;
			if (dc % 2 != 0) dc++;
			if (steps/2 == dc) {
				dc = -dc;
			}
			long endY = curY+dc;
			endY %= cz;
			endY += cz;
			endY %= cz;
			//System.out.println("ENDX: " + endX + " ENDY: " + endY);
			curX = (int)endX;
			curY = (int)endY;
			dir = (int)((steps)%4l);
			curColor = (char)('A' + (int)((steps)%26l));
			stepSize = steps+1;
			//System.out.println("LASTDIR1: " + tdir);
			//System.out.println("LASTCOLOR1: " + tcurColor);
			steps = 10000;

		}
		//System.out.println("STEPS: " + steps);
		grid[curX][curY] = curColor;
		for (long i = 0; i < steps; i++) {
			int startX = curX;
			int startY = curY;
			grid[curX][curY] = curColor;
			for (long j = 0; j < stepSize; j++) {

				curX += dx[dir];
				curY += dy[dir];

				if (curX < 0) curX += rz;
				curX %= rz;
				if (curY < 0) curY += cz;
				curY %= cz;
				grid[curX][curY] = curColor;
				if (curX == startX && curY == startY) {
					curX = (int)(startX%rz + dx[dir]*(stepSize%rz));
					curY = (int)(startY%cz + dy[dir]*(stepSize%cz));
					curX %= rz;
					curY %= cz;
					curX += rz;
					curY += cz;
					curX %= rz;
					curY %= cz;
					break;
				}
			}
			if (curColor == 'Z') curColor = 'A';
			else curColor++;
			dir++;
			dir%=4;
			stepSize++;
		}
		//System.out.println("LASTPOS: (" + curX + "," + curY + ")");
		//System.out.println("LASTDIR: " + dir);
		//System.out.println("LASTCOLOR: " + curColor);
		print(grid, curX, curY);
	}

	static void print(char[][] grid, int x, int y) {
		StringBuilder br = new StringBuilder("");
		for (int j = 0; j < grid.length; j++) {
			for (int i = 0; i < grid[j].length; i++) {
				if (j == x && i == y) 
					br.append('@');
					else
				br.append(grid[j][i]);
			}
			br.append("\n");
		}
		System.out.print(br.toString());
	}
}