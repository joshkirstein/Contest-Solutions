
/*
ID: joshkir1
LANG: JAVA
TASK: kimbits
*/
import java.io.*;
import java.util.*;

public class kimbits {

    static BufferedWriter out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("kimbits.in"));
        out = new BufferedWriter(new FileWriter("kimbits.out"));
        long N = sc.nextLong(), L = sc.nextLong(), I = sc.nextLong();
	run(N, L, I);
	out.append("\n");
        out.close();
    }

    public static void run(long digitsLeft, long onesLeft, long count) throws IOException {
	if (digitsLeft <= 0) return;
        long count0 = 1;
	for (int i = 1; i <= onesLeft; i++) {
	    //putting i ones.
	    long interior = 1;
	    long temp = i;
	    long digs = digitsLeft-1;
	    int j = 2;
	    boolean hit = false;
	    while (temp > 0 && digs > 0) {
		hit = true;
		interior *= digs;
		digs--;
		temp--;
		if (j <= i) {
		    if (interior % j == 0) {
			interior /= j;
			j++;
		    }
		    
		}
	    }
	    if (!hit) continue;
	    for (; j <= i; j++) interior /= j;
	    count0 += interior;
	}
	if (count0 >= count) {
	    out.append("0");
	    run(digitsLeft-1, onesLeft, count);
	} else {
	    out.append("1");
	    run(digitsLeft-1, onesLeft-1, count-count0);
	}
    }
}
