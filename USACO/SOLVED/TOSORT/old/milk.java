package old;

/*
ID: joshkir1
LANG: JAVA
TASK: milk
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class milk {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("milk.in")));
        PrintWriter out = new PrintWriter(new FileWriter("milk.out"));
        String[] info = sc.nextLine().split(" ");
        int goal = Integer.parseInt(info[0]);
        int farmers = Integer.parseInt(info[1]);
        ArrayList<Farmer> farms = new ArrayList<Farmer>();
        for (int i = 0; i < farmers; i++) {
            String[] in = sc.nextLine().split(" ");
            farms.add(new Farmer(Integer.parseInt(in[0]), Integer.parseInt(in[1])));
        }
        Collections.sort(farms);
        int cost = 0;
        int left = goal;
        while (left > 0) {
            Farmer first = farms.remove(0);
            if (left >= first.amt) {
                cost += (first.cost * first.amt);
                left -= first.amt; 
            } else {
                cost += (first.cost * left);
                left = 0;
            }
        }
        out.println(cost);
        out.close();
        System.exit(0);
    }
    
    static class Farmer implements Comparable<Farmer> {
        
        public int cost, amt;
        
        public Farmer(int cost, int amt) {
            this.cost = cost;
            this.amt = amt;
        }
        
        public String toString() {
            return "(" + cost + ", " + amt + ")";
        }

        public int compareTo(Farmer o) {
            return cost - o.cost;
        }
    }
}
