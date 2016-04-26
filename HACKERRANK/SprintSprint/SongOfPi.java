import java.util.*;

public class SongOfPi {
	static String DIGITS = "31415926535897932384626433833";
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cases = Integer.parseInt(sc.nextLine());
		MAIN:
		while (cases-->0) {
			String[] spl = sc.nextLine().split(" ");
			for (int i = 0; i < Math.min(spl.length, DIGITS.length()); i++) {
				int size = spl[i].length();
				if (size != Integer.parseInt(DIGITS.charAt(i) + "")) {
					System.out.println("It's not a pi song.");
					continue MAIN;
				}
			}
			System.out.println("It's a pi song.");
		}
	}
}