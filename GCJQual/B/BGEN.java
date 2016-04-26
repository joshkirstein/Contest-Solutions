import java.util.*;

public class BGEN {
	static Random rnd = new Random();
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println(100);
		for (int i = 0; i < 100; i++) {
			int people = 1000;
			System.out.println(people);
			for (int j = 0; j < people; j++) {
				int person = rnd.nextInt(1000)+1;
				if (j == 0) {
					System.out.print(person);
				} else {
					System.out.print(" " + person);
				}
			}
				System.out.println();
		}
	}
}