import java.util.*;

class E {
	static boolean isHill(String str) {
		//TODO:
		if (str.length() <= 1) return true;
		int fallen = 0;
		for (int i = 0; i < str.length()-1; i++) {
			int d = dig(str, i);
			int d2 = dig(str, i+1);
			if (d2 < d) fallen++;
			if (d < d2 && fallen != 0) return false;
		}
		return true;
	}
	
	static int dig(String str, int val) {
		return (int) (str.charAt(val) - '0');
	}

	static String str;
	static int length;
	static Long[][][] memo1;
	static Long[][][] memo2;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long n = in.nextLong();
		str = Long.toString(n);
		length = str.length();
		if (!isHill(str)) {
			System.out.println("-1");
			return;
		}
		memo1 = new Long[10][20][2];
		memo2 = new Long[10][20][2];
		long answer = 0;
		for (int i = 0; i < length; i++) {
			int dig = (int)(str.charAt(i) - '0');
			int size = length-i;
			
			if (size == length) {
				for (int j = 1; j < dig; j++) {
					answer += count(j, size-1, true);
				}
				answer += wCount(dig, size-1, 1, true);
			} else {
				for (int j = 1; j <= 9; j++) {
					answer += count(j, size-1, true);
				}
			}
		}
		
		System.out.println("" + answer);
	}
	
	static long wCount(int lastDig, int sizeLeft, int pos, boolean incr) {
		if (memo1[lastDig][sizeLeft][conv(incr)] != null) return memo1[lastDig][sizeLeft][conv(incr)];
		if (sizeLeft == 0) return 1;
		int at = (int)(str.charAt(pos) - '0');
		if (incr) {
			long sum = 0;
			//incr
			for (int j = lastDig; j <= at; j++) {
				if (j == at) {
					sum += wCount(j, sizeLeft-1, pos+1, true);
				} else {
					sum += count(j, sizeLeft-1, true);
				}
			}
			
			//decr
			if (at < lastDig) {
				for (int j = 0; j < at; j++) {
					sum += count(j, sizeLeft-1, false);
				}
				sum += wCount(at, sizeLeft-1, pos+1, false);
			} else {
				for (int j = 0; j < lastDig; j++) {
					if (j == at) {
						sum += wCount(j, sizeLeft-1, pos+1, false);
					} else {
						sum += count(j, sizeLeft-1, false);
					}
				}
			}
			return memo1[lastDig][sizeLeft][conv(incr)] = sum;
		} else {
			long sum = 0;
			if (at < lastDig) {
				for (int j = 0; j < at; j++) {
					sum += count(j, sizeLeft-1, false);
				}
				sum += wCount(at, sizeLeft-1, pos+1, false);
			} else {
				for (int j = 0; j <= lastDig; j++) {
					if (j == at) {
						sum += wCount(j, sizeLeft-1, pos+1, false);
					} else {
						sum += count(j, sizeLeft-1, false);
					}
				}
			}
			return memo1[lastDig][sizeLeft][conv(incr)] = sum;
		}
	}
	
	static int conv(boolean b) {
		if (b) return 1;
		else return 0;
	}
	
	static long count(int lastDig, int sizeLeft, boolean incr) {
		if (memo2[lastDig][sizeLeft][conv(incr)] != null) return memo2[lastDig][sizeLeft][conv(incr)];
		if (sizeLeft == 0) return 1;
		if (incr) {
			long sum = 0;
			//incr
			for (int j = lastDig; j <= 9; j++) {
				sum += count(j, sizeLeft-1, true);
			}
			
			//decr:
			for (int j = 0; j < lastDig; j++) {
				sum += count(j, sizeLeft-1, false);
			}
			return memo2[lastDig][sizeLeft][conv(incr)] = sum;
		} else {
			long sum = 0;
			for (int j = 0; j <= lastDig; j++) {
				sum += count(j, sizeLeft-1, false);
			}
			return memo2[lastDig][sizeLeft][conv(incr)] = sum;
		}
	}
}
