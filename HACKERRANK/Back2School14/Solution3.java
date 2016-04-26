import java.util.*;
import java.math.*;

public class Solution3 {
	
	static long MOD = 1000000007;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(), K = sc.nextInt();
		Point[] pts = new Point[N];
		for (int i = 0; i < pts.length; i++) {
			pts[i] = new Point(sc.nextInt(), sc.nextInt());
		}
		doWork2(pts, K);
	}

	public static void doWork2(Point[] pts, int K) {
		long ansW = nCk(pts.length, K);
		int minX = pts[0].x, maxX = pts[0].x;
		int minY = pts[0].y, maxY = pts[0].y;
		for (int i = 0; i < pts.length; i++) {
			minX = Math.min(minX, pts[i].x);
			maxX = Math.max(maxX, pts[i].x);
			minY = Math.min(minY, pts[i].y);
			maxY = Math.max(maxY, pts[i].y);
		}
		int minXCnt = 0;
		int minYCnt = 0;
		int maxXCnt = 0;
		int maxYCnt = 0;
		int ov1 = 0;//min min
		int ov2 = 0;//max max
		int ov3 = 0;//min max
		int ov4 = 0;//max min
		int inside = 0;
		for (int i = 0; i < pts.length; i++) {
			Point p = pts[i];
			boolean val = false;
			if (p.x == minX) {
				val = true;
				minXCnt++;
			}
			if (p.x == maxX) {
				val = true;
				maxXCnt++;
			}
			if (p.x == minX && p.y == minY) {
				ov1++;
			}
			if (p.x == maxX && p.y == maxY) {
				ov2++;
			}
			if (p.x == minX && p.y == maxY) {
				ov3++;
			}
			if (p.x == maxX && p.y == minY) {
				ov4++;
			}
			if (p.y == minY) {
				val = true;
				minYCnt++;
			} 
			if (p.y == maxY) {
				val = true;
				maxYCnt++;
			}
			if (val) inside++;
		}
		int onlyInside = pts.length - inside;
		//how many ways can we get so that no area changes.
		long sub = 0;
		int[] arr = {K-minXCnt, K-maxXCnt, K-minYCnt, K-maxYCnt};
		boolean[] marked = new boolean[4];
		System.out.println("ANSW: " + ansW);
		for (int i = 0; i < arr.length; i++) {
			int amtHere = K - arr[i];
			if (arr[i] <= 0) continue;
			long temp = 0;

			if (i == 0) {
				int sum = ov1 + ov3;
				int canChoose = amtHere - sum;
				if (sum != 0) {
					if (ov1 != 0 && ov3 != 0) {

					}
					if (ov1 != 0) {//minmin

					}
					if (ov3 != 0) {//minmax

					}
				}
				int internal = 0;
				for (int j = 0; j < arr.length; j++) {
					if (i != j) {
						int amtJ = K - arr[j];
						if (amtJ - 2 >= 0) {
							internal += amtJ - 2;
						}
					}
				}
				internal += onlyInside;
				temp += nCk(internal, K - (amtHere)+1);
			} else if (i == 1) {

			} else if (i == 2) {

			} else if (i == 3) {

			}

			sub += temp;
			sub %= MOD;
		}
		System.out.println("SUB: " + sub);

		long first = ansW - sub;
		if (first < 0) first += MOD;
		first %= MOD;
		System.out.println(first);
	}

	public static void doWork(Point[] pts, int K) {
		int minX = pts[0].x, maxX = pts[0].x;
		int minY = pts[0].y, maxY = pts[0].y;
		for (int i = 0; i < pts.length; i++) {
			minX = Math.min(minX, pts[i].x);
			maxX = Math.max(maxX, pts[i].x);
			minY = Math.min(minY, pts[i].y);
			maxY = Math.max(maxY, pts[i].y);
		}
		int minXCnt = 0;
		int minYCnt = 0;
		int maxXCnt = 0;
		int maxYCnt = 0;
		int ov1 = 0;//min min
		int ov2 = 0;//max max
		int ov3 = 0;//min max
		int ov4 = 0;//max min
		for (int i = 0; i < pts.length; i++) {
			Point p = pts[i];
			if (p.x == minX) {
				minXCnt++;
			}
			if (p.x == maxX) {
				maxXCnt++;
			}
			if (p.x == minX && p.y == minY) {
				ov1++;
			}
			if (p.x == maxX && p.y == maxY) {
				ov2++;
			}
			if (p.x == minX && p.y == maxY) {
				ov3++;
			}
			if (p.x == maxX && p.y == minY) {
				ov4++;
			}
			if (p.y == minY) {
				minYCnt++;
			} 
			if (p.y == maxY) {
				maxYCnt++;
			}
		}
		int[] arr = {K-minXCnt, K-maxXCnt, K-minYCnt, K-maxYCnt};
		boolean[] marked = new boolean[4];
		long amt = 0;
		for (int i = 0; i < arr.length; i++) {
			marked[i] = true;
			int canRemove = arr[i]; // can remove this amt
			if (canRemove > 0) {
				int cFrom = pts.length;
				cFrom -= (K-canRemove);
				System.out.println("I: " + i + " US: " + (K-canRemove));
				for (int j = 0; j < arr.length; j++) {
					if (i == j) continue;
					int select = K-arr[j]; // amt there.
					//take out overlap...
					if ((i == 0 && j == 2) || (i == 2 && j == 0)) {
						//take out min min
						select -= ov1;
					} else if ((i == 0 && j == 3) || (j == 0 && i == 3)) {
						//take out min max
						select -= ov3;
					} else if ((i == 1 && j == 2) || (i == 2 && j == 1)) {
						//take out max min
						select -= ov4;
					} else if ((i == 1 && j == 3) || (j == 1 && i == 3)) {
						//take out max max
						select -= ov2;
					}
					System.out.print("J: " + j  + " SELECT: " + select);
					if (marked[j] && select <= canRemove) {
						System.out.print("CUTOUT");
						//System.out.println("wtf: " + (K - arr[j]));
						cFrom -= select;
					}
					System.out.println();
				}	
				amt += nCk(cFrom, canRemove);
				amt %= MOD;
				System.out.println("I: " + i + " CANREMOVE: " + canRemove + " cFROM: " + cFrom);
				System.out.println("COUNT? " + nCk(cFrom, canRemove));
			} else if (canRemove == 0) {
				//System.out.println("COUNT: 1");
				amt += 1;
				amt %= MOD;
			} else {
				//canRemove < 0
			}
		}
		for (int c : arr) {
			if (c >= 0) {
				int removed = K - c;
				int N = pts.length;
				//System.out.println("COUNT: " + nCk(N-removed, removed));
			}
		}
		System.out.println(amt);
		//System.out.println("A1: " + (K-minXCnt));
		//System.out.println("A2: " + (K-maxXCnt));
		//System.out.println("A3: " + (K-minYCnt));
		//System.out.println("A4: " + (K-maxYCnt));
	}


    public static long nCk(long n, long k) {
    	if (k > n) return 0;
    	if (k == n) return 1;
        long top = factP(n, MOD);
        long bot = factP(k, MOD);
        long botOther = factP(n-k, MOD);
        long botBot = ((bot%MOD)*(botOther%MOD))%MOD;
        BigInteger mul = BigInteger.valueOf(top);
        BigInteger inv = BigInteger.valueOf(botBot).modInverse(BigInteger.valueOf(MOD));
        long val = inv.longValue();
        return (top*val)%MOD;
    }

    public static long factP(long n, long p) {
        long val = 1;
        for (long i = 1; i <= n; i++) {
            val = val * i;
            val %= p;
        }
        return val;
    }
}
class Point {
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x, y;
}