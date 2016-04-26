
/*
 ID: joshkir1
 LANG: JAVA
 TASK: concom
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class concom {

    static int[][] input;
    static int[][] indirect;
    static int max;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("concom.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("concom.out"));
        //input processing output here
        int N = Integer.parseInt(sc.readLine());
        input = new int[101][101];
        indirect = new int[101][101];
        max = 1;
        class Pair {

            public int i, j, val;
        }
        Queue<Pair> q = new LinkedList<Pair>();
        for (int x = 0; x < N; x++) {
            String[] spl = sc.readLine().split(" ");
            int i = Integer.parseInt(spl[0]),
                    j = Integer.parseInt(spl[1]),
                    k = Integer.parseInt(spl[2]);
            max = Math.max(max, Math.max(i, j));
            input[i][j] = k;
            //System.out.println("INPUT[" + i + "," + j + "] = " + k);
            if (k > 50) {
                Pair add = new Pair();
                add.i = i;
                add.j = j;
                add.val = k;
                q.add(add);
            }
            indirect[i][j] = k;
        }
        //Idea: queue all currently valid edges
        //if these current valid edges create any new ones, add them to the queue
        //dont process stuff we KNOW is good...
        //GOTCHA: since a company controls itself,
        //its own percent can count as an 'indirect' percentage...adding indirect[i][j] = k at input read
        //fixed the issue :)
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Pair pop = q.poll();
                //System.out.println("EXAMINING (" + pop.i + "," + pop.j + "," + pop.val + ");");
                for (int j = 1; j <= max; j++) {
                    if (pop.i == j || pop.j == j) {
                        continue;
                    }
                    if (indirect[pop.i][j] > 50 || input[pop.i][j] > 50) {
                        continue;
                    }
                    indirect[pop.i][j] += input[pop.j][j];
                    //System.out.println("INDIRECT[" + pop.i + "," + j + "] = " + indirect[pop.i][j]);
                    //System.out.println("BY ADDING INPUT[" + pop.j + "," + j + "] = " + input[pop.j][j]);
                    if (indirect[pop.i][j] > 50) {
                        Pair p = new Pair();
                        p.i = pop.i;
                        p.j = j;
                        q.add(p);
                        /*for (int k = 1; k <= max; k++) {
                            if (input[k][pop.i] > 50 || indirect[k][pop.i] > 50) {
                                Pair p2 = new Pair();
                                p2.i = k;
                                p2.j = pop.i;
                                q.add(p2);
                            }
                        }*/
                    }
                }
            }
        }
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= max; j++) {
                if (i != j) {
                    if ((indirect[i][j]) > 50) {
                        out.append(i + " " + j + "\n");
                    }
                }
            }
        }
        out.close();
    }
}