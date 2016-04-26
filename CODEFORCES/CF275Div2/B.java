import java.util.*;

public class B {
	
	static long cnt1, cnt2, x, y;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		cnt1 = sc.nextLong();
		cnt2 = sc.nextLong();
		x = sc.nextLong();
		y = sc.nextLong();
		long lo = 1;
		long hi = (long)1e12;
		while (hi > lo) {
			long mid = lo + (hi-lo)/2;
			if (works(mid)) {
				hi = mid;
			} else {
				lo = mid+1;
			}
		}
		System.out.println(lo);
	}

	public static boolean works(long v) {
		long num1 = v/x;
		long num2 = v/y;
		long num3 = v/(x*y);
		//System.out.println("NUM3: " + num3 + " V: " + v);
		return (((v-num1) >= cnt1 && (v-num2) >= cnt2)) && (v-num3 >= (cnt1+cnt2));
	}
}