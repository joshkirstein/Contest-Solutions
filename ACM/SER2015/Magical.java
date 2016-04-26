import java.util.*;
import java.io.*;
public class Magical {
	
	public static void main(String[] args) throws Exception {
		File secret = new File("/Users/joshkirstein/Desktop/data 6/secret/");
		File[] files = secret.listFiles();
		for (File f : files) {
			if (f.toString().contains(".ans") || f.toString().contains("Store")) continue;
			File out = new File(f.getAbsolutePath().replace(".in", ".ans"));
			Scanner sc = new Scanner(new FileReader(f));
			Scanner sc2 = new Scanner(new FileReader(out));
			long N = sc.nextInt()-3;
			int answer = sc2.nextInt();
			long smallest = N;
			for (long i = 2; i*i <= N; i++) {
				if (N%i == 0) {
					long val1 = N/i;
					long val2 = i;
					if (val1 > 3) smallest = Math.min(smallest, val1);
					if (val2 > 3) smallest = Math.min(smallest, val2);
				}
			}
			if (smallest != answer)
			System.out.println(smallest + "," + answer + " N: " + N);
		}
	}
}