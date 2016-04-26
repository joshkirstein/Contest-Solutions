import java.util.*;

class Shuffles {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Boolean[] map = new Boolean[N+1];
		for (int i = 0; i < N; i++) {
			int val = sc.nextInt();
			int minus = val-1;
			Boolean arr = null;
			if (minus >= 1) {
				if (map[minus] != null) {
					arr = map[minus];
					map[minus] = null;
				}
			}
			if (arr == null) {
				arr = true;
			}
			map[val] = arr;
		}
		int cnt = 0;
		for (Boolean a : map) {
			if (a != null) cnt++;
		}
		int amt = 0;
		int val = 1;
		while (val < cnt) {
			amt++;
			val <<= 1;
		}
		System.out.println(amt);
	}
}