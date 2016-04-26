package Section24;


/*
ID: joshkir1
LANG: JAVA
TASK: comehome
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class comehome {
    
    //USES ALL PAIRS SHORTEST PATH
    //Possible alternative: dijkstra using barn ('Z') as source!
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("comehome.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("comehome.out"));
        int[][] adjMat = new int[58][58];
        ArrayList<Character> capsUsed = new ArrayList<Character>();
        int paths = Integer.parseInt(sc.readLine());
        for (int i = 0; i < adjMat.length; i++) {
            Arrays.fill(adjMat[i], 500000);
        }
        for (int i = 0; i < paths; i++) {
            String[] spl = sc.readLine().split(" ");
            char u = spl[0].charAt(0);
            char v = spl[1].charAt(0);
            int weight = Integer.parseInt(spl[2]);
            adjMat[map(u)][map(v)] = Math.min(weight, adjMat[map(u)][map(v)]);//this is a multigraph, just take minimum edge weight
            adjMat[map(v)][map(u)] = Math.min(weight, adjMat[map(v)][map(u)]);//since its the only one that will count
            if (Character.isUpperCase(u)) {
                capsUsed.add(u);
            }
            if (Character.isUpperCase(v)) {
                capsUsed.add(v);
            }
        }
        for (int k = 0; k < adjMat.length; k++) {//intermediate...
            for (int i = 0; i < adjMat.length; i++) {
                for (int j = 0; j < adjMat.length; j++) {
                    if (adjMat[i][k] + adjMat[k][j] < adjMat[i][j]) {
                        adjMat[i][j] = adjMat[i][k] + adjMat[k][j];
                    }
                }
            }
        }
        int min = Integer.MAX_VALUE;
        char minChar = '0';
        int zMap = map('Z');
        for (char c : capsUsed) {
            if (c != 'Z') {
                if (adjMat[map(c)][zMap] < min) {
                    min = adjMat[map(c)][zMap];
                    minChar = c;
                }
            }
        }
        //System.out.println(adjMat[map('R')][map('Z')]);
        out.append(minChar + " " + min + "\n");
        out.close();
    }
    
    public static int map(char c) {
        return ((int) c) - 65;
    }
}