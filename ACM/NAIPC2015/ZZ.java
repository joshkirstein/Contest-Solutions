import java.util.*;

public class ZZ {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int K = sc.nextInt();
		System.out.println(get(K));
	}

	static String get(int K) {
		int chars = K/25;
		if (K%25!=0) chars++;
		chars++;
		char[] out = new char[chars];
		int cst = 25*(chars-1);
		for (int i = 0; i < chars; i++) {
			if (i % 2 == 0) {
				out[i] = 'a';
			} else {
				out[i] = 'z';
			}
			if (i == 1) {
				if (i != out.length-1) {
					while (cst-K>=2) {
						cst-=2;
						out[i]--;
					}
				} else {
					while (cst-K>0) {
						cst--;
						out[i]--;
					}
				}
			}
			if (i == out.length-1) {
				while (cst-K>0) {
					if (i%2!=0) {
						cst--;
						out[i]--;
					} else {
						cst--;
						out[i]++;
					}
				}
			}
		}
		return String.valueOf(out);
	}
}