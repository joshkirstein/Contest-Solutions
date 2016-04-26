package Section24;


/*
ID: joshkir1
LANG: JAVA
TASK: cowtour
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class cowtour {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("cowtour.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("cowtour.out"));
        //input processing output here
        int N = Integer.parseInt(sc.readLine());
        int[] x = new int[N];
        int[] y = new int[N];
        double[][] adj = new double[N][N];
        for (int i = 0; i < N; i++) {
            String[] spl = sc.readLine().split(" ");
            x[i] = Integer.parseInt(spl[0]);
            y[i] = Integer.parseInt(spl[1]);
            adj[i][i] = 0;
        }
        for (int i = 0; i < N; i++) {
            String s = sc.readLine();
            for (int j = 0; j < N; j++) {
                if (s.charAt(j) == '1') {
                    adj[i][j] = Math.sqrt(Math.pow(x[i]-x[j], 2) + Math.pow(y[i]-y[j], 2));
                    adj[j][i] = adj[i][j];
                } else if (s.charAt(j) == '0') {
                    adj[i][j] = 9999999;//just a bif value...9999999 is excessive
                    adj[j][i] = 9999999;
                }
            }
        }
        //run floyd warshall...determine connected pieces...:
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    //continue;//not useable...
                    if (adj[i][k] == 9999999 || adj[k][j] == 9999999) continue;
                    if (adj[i][k] + adj[k][j] < adj[i][j]) {
                        adj[i][j] = adj[i][k] + adj[k][j];
                    }
                }
            }
        }
        /*for (double[] arr : adj) {
            //System.out.println(Arrays.toString(arr));
        }*/
        double maxMax = Double.MAX_VALUE;
        double maxSmall = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (adj[i][j] >= 9999999 && i != j) {
                    //not connected, possible candidate...
                    double distExtra = Math.sqrt(Math.pow(x[i]-x[j], 2) + Math.pow(y[i]-y[j], 2));
                    double maxDistI = 0;
                    double maxDistJ = 0;
                    for (int k = 0; k < N; k++) {
                        if (adj[i][k] < 9999999 && i != k) {//lol the whole time it was the last conditional :(
                            maxDistI = Math.max(maxDistI, adj[i][k]);
                        }
                        if (adj[j][k] < 9999999 && j != k) {
                            maxDistJ = Math.max(maxDistJ, adj[j][k]);
                        }
                    }
                    distExtra += maxDistI + maxDistJ;
                   // System.out.println(distExtra);
                    maxMax = Math.min(maxMax, distExtra);
                    maxSmall = Math.max(maxSmall, Math.max(maxDistI, maxDistJ));//these are old field diameters...
                    //we need these because DIFFERENT fields can be nested INSIDE eachother...
                    //so we just want the max overall.
                }
            }
        }
        maxMax = Math.max(maxMax, maxSmall);//maxSmall is a gotcha because of the possibility of a nested field
        System.out.println(maxMax);
        out.append(String.format("%.6f\n", maxMax));
        out.close();
    }
}