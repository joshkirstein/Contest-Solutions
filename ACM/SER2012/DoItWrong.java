import java.util.*;
import java.math.*;

public class DoItWrong {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder br = new StringBuilder("");
		while (true) {
			ArrayList<Frac> out = new ArrayList<Frac>();

			long b = sc.nextInt();
			long n = sc.nextInt();
			if (b == 0 && n == 0) break;

			for (long m = 1; m <= 2*n; m++) {
				long top = 2*n*b-b*m;
				top *= m;
				long bot = n*n;
				if (top % bot == 0) {
					long a = top/bot;			
					long top1 = m;		
					if (top1 == n && a == b) continue;
					if (top1 >= 0) {
						if (a == 0 && top1 == 0) {

						} else
						out.add(new Frac(a, top1));
					}
				}
			}

			Collections.sort(out);
			if (!out.isEmpty()) {
				br.append(out.get(0));
			}
			for (int i = 1; i < out.size(); i++) {
				br.append(" " + out.get(i));
			}
			br.append("\n");
		}
		System.out.print(br.toString());
	}
}
class Frac implements Comparable<Frac> {
	public Frac(long a, long b) {
		this.a = a;
		this.b = b;
		if (gcd(a,b) != 0) {
		this.gcda = a / gcd(a,b);
		this.gcdb = b/gcd(a,b);
	}
	}
	public long a, b;
	public long gcda, gcdb;
	public int compareTo(Frac f) {
		if (gcda == f.gcda && gcdb == f.gcdb) {
			return Long.compare(a, f.a);
		} else {
			return Long.compare(a*f.b, b*f.a);
		}
	}

	public long gcd(long a, long b) {
		if (b == 0) return a;
		if (a == 0) return b;
		return gcd(b, a%b);
	}

	public String toString() {
		return a + "/" + b;
	}
}
