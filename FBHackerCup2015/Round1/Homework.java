import java.util.*;
import java.io.*;
public class Homework {

	static Integer[] primacity = new Integer[10_000_000+1];
	static boolean[] isPrime = new boolean[10_000_000+1];
	static ArrayList<Integer> primes = new ArrayList<Integer>();

	static {
		for (int i = 1; i <= 10000000; i++) {
			if (isPrime(i)) {primes.add(i);
				isPrime[i] = true;
			}
		}
		primacity[1] = 0;
		primacity[0] = 0;
		for (int i = 2; i <= 10000000; i++) {
			primacity[i] = prim(i);
			//if (i % 100000 == 0 || primacity[i] >= 10)
			//System.out.println("I: " + i + " PR: "+ primacity[i]);
		}
	}

	static int prim(int x) {
		int oldx = x;
		if (isPrime[x]) return 1;
		int curP = 0;
		int cnt = 0;
		while (true) {
			int curPrime = primes.get(curP);
			if (primacity[x] != null) {
				return cnt+primacity[x];
			}
			if (curPrime > x) break;
			if (x % curPrime == 0)
				cnt++;
			while (x % curPrime == 0) x /= curPrime;
			curP++;
		}
		if (x != 1) cnt++;
		primacity[oldx] = cnt;
		return cnt;
	}

	static boolean isPrime(int x) {
		if (x == 0 || x == 1) return false;
		if (x == 2) return true;
		if (x % 2 == 0) return false;
		int sq = ((int) Math.sqrt(x))+1;
		for (int i = 3; i < sq; i += 2) {
			if (x % i == 0) return false;
		}
		return true;
	}
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("WAITING");
		if (sc.next().equals("x")) {
			BufferedReader br = new BufferedReader(new FileReader("./test.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("./output.txt"));
			int cases = Integer.parseInt(br.readLine());
			for (int x = 1; x <= cases; x++) {
				String[] spl = br.readLine().split(" ");
				int lo = Integer.parseInt(spl[0]);
				int hi = Integer.parseInt(spl[1]);
				int k = Integer.parseInt(spl[2]);
				if (k >= 10) {
					bw.append("Case #" + (x) + ": 0\n");
				} else {
					int cnt = 0;
					for (int i = lo; i <= hi; i++) {
						if (primacity[i] == k) cnt++;
					}
					bw.append("Case #" + (x) + ": " + cnt + "\n");
				}
			}

			bw.flush();
			bw.close();
		}
	}
}