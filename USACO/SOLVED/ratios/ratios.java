
/*
ID: joshkir1
LANG: JAVA
TASK: ratios
*/
import java.io.*;
import java.util.*;

public class ratios {

    static BufferedWriter out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("ratios.in"));
        out = new BufferedWriter(new FileWriter("ratios.out"));
        final int x=sc.nextInt(), y = sc.nextInt(), z = sc.nextInt();
	final int[][] eqns = new int[3][3];
	for (int i = 0; i < 3; i++) 
	    for (int j = 0; j < 3; j++) eqns[i][j] = sc.nextInt();
	int min = Integer.MAX_VALUE;
	int[] res = null;
	int koef = -1;
        for (int i = 0; i <= 100; i++) {
	    for (int j = 0; j <= 100; j++) {
		for (int k = 0; k <= 100; k++) {
		    int[] sum = new int[3];
		    sum[0] = i*eqns[0][0]+j*eqns[1][0]+k*eqns[2][0];
		    sum[1] = i*eqns[0][1]+j*eqns[1][1]+k*eqns[2][1];
		    sum[2] = i*eqns[0][2]+j*eqns[1][2]+k*eqns[2][2];
		    if (x == 0) if (sum[0] != 0) continue;
		    if (x != 0) if (sum[0] == 0) continue;

		    if (y == 0) if (sum[1] != 0) continue;
		    if (y != 0) if (sum[1] == 0) continue;

		    if (z == 0) if (sum[2] != 0) continue;
		    if (z != 0) if (sum[2] == 0) continue;
		    
		    int coef = -1;
		    
		    if (x != 0 && sum[0] != 0) {
			if (sum[0] < x) continue;
			if (sum[0] % x != 0) continue;
			int div = sum[0] / x;
			coef = div;
		    }

		    if (y!=0&&sum[1]!=0) {
			if (sum[1] < y) continue;
			if (sum[1] % y != 0) continue;
			int div = sum[1] / y;
			if (coef != -1) if (coef != div) continue;
			coef = div;
		    }

		    if (z!=0&&sum[2]!=0) {
			if (sum[2] < z) continue;
			if (sum[2] % z != 0) continue;
			int div = sum[2] / z;
			if (coef != -1) if (coef != div) continue;
			coef = div;
		    }
		    if ((i+j+k) < min) {
			min = i+j+k;
		        res = new int[] { i, j, k };
			koef= coef;
		    }
		}
	    }
	}
	if (min == Integer.MAX_VALUE) {
	    out.append("NONE\n");
	} else {
	    out.append(res[0] + " " + res[1] + " " + res[2] + " " + koef + "\n");
	}
        out.close();
    }
}
