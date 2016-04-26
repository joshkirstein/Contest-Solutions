import java.util.*;

public class CGEN {
	static Random rnd = new Random();
	public static void main(String[] args) {
		System.out.println(100);
		for (int i = 0; i < 100; i++) {
			int L = 10000;
			long X = 1000000000000l;
			System.out.println(L + " " + X);
			System.out.println(random(L));
		}
	}

	static String random(int len) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			int r = rnd.nextInt(3);
			if (r == 0) str[i] = 'i';
			if (r == 1) str[i] = 'j';
			if (r == 2) str[i] = 'k';
		}
		return String.valueOf(str);
	}
}