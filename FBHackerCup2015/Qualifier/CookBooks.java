import java.util.*;

public class CookBooks {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int caze = 0;
		while (T-- > 0) {
			caze++;
			int N = sc.nextInt();
			String s = String.valueOf(N);
			int max = -1;
			int min = -1;
			for (int i = 0; i < s.length(); i++) {
				int dig = (Integer.parseInt(s.charAt(i) + ""));
				for (int j = i+1; j < s.length(); j++) {
					int other = (Integer.parseInt(s.charAt(j) + ""));
					if (other < dig && min == -1) {
						if (i == 0 && other == 0) {} else {
						//can make smaller!
						min = Integer.parseInt(swap(s, i, j));
						}
					}
					if (other > dig && max == -1) {
						//can make bigger!
						max = Integer.parseInt(swap(s, i, j));
					}
				}
			}
			if (max == -1) max = N;
			if (min == -1) min = N;
			System.out.println("Case #" + caze + ": " + min + " " + max);
		}
	}

	static String swap(String s, int i, int j) {
		char[] v = s.toCharArray();
		char temp = v[i];
		v[i] = v[j];
		v[j] = temp;
		return String.valueOf(v);
	}
}