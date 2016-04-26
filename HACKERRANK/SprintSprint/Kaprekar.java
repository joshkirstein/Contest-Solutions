import java.util.*;

public class Kaprekar {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int p = sc.nextInt();
		int q = sc.nextInt();
		ArrayList<Integer> out = new ArrayList<Integer>();

		for (int i = p; i <= q; i++) {
			if (isKap(i)) out.add(i);
		}

		if (out.isEmpty()) {
			System.out.println("INVALID RANGE");
		} else {
			System.out.print(out.get(0));
			for (int i = 1; i < out.size(); i++) {
				System.out.print(" " + out.get(i));
			}
			System.out.println();
		}
		
	}

	static boolean isKap(long number) {
		try {
		String sq = String.valueOf(number*number);
		String str = String.valueOf(number);
		String res1 = sq.substring(0, sq.length()-str.length());
		String res2 = sq.substring(sq.length()-str.length(), sq.length());
		if (res1.equals("")) res1 = "0";
		if (res2.equals("")) res2 = "0";
		if (res1.length() != res2.length() && res1.length() != res2.length()-1) return false;
		int one = Integer.parseInt(res1);
		int two = Integer.parseInt(res2);

		return (one+two)==(number);
		} catch (Exception ex) {return false;}
	}
}