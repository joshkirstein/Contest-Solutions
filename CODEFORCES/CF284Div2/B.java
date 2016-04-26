import java.util.*;

public class B {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < m; i++) {
			String u = sc.next();
			String v = sc.next();
			map.put(u, v);
		}
		StringBuilder br = new StringBuilder("");
		for (int i = 0; i < n; i++) {
			String word = sc.next();
			String other = map.get(word);
			if (i != 0) br.append(" ");
			if (other.length() < word.length()) {
				br.append(other);
			} else {
				br.append(word);
			}
		}
		System.out.println(br.toString());
	}
}