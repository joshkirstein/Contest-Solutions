package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: acrobat
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class acrobat {
    
    static class Cow {
            
            public int weight, strength;
            public Cow(int weight, int strength) {
                this.weight = weight;
                this.strength = strength;
            }
        }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("acrobat.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("acrobat.out"));
        int numCows = Integer.parseInt(sc.readLine());
        
        final ArrayList<Cow> curOrder = new ArrayList<Cow>(100);
        PriorityQueue<Cow> cows = new PriorityQueue<Cow>(1, new Comparator<Cow>() {
            public int compare(Cow one, Cow two) {
                int rOne = getRisk(curOrder.get(curOrder.size() - 1), one);
                int rTwo = getRisk(curOrder.get(curOrder.size() - 1), two);
                return rOne - rTwo;
            }
        });
        for (int i = 0; i < numCows; i++) {
            String[] spl = sc.readLine().split(" ");
            int w = Integer.parseInt(spl[0]);
            int s = Integer.parseInt(spl[1]);
            if (curOrder.isEmpty() || curOrder.get(0) == null) {
                curOrder.add(new Cow(w, s));
                continue;
            }else if ((curOrder.get(0).weight - curOrder.get(0).strength) < (w - s)) {
                cows.add(curOrder.get(0));
                curOrder.set(0, new Cow(w, s));
                continue;
            }
            cows.add(new Cow(w, s));
        }
        while (!cows.isEmpty()) {
            Cow pop = cows.poll();
            curOrder.add(pop);
        }
        int sumWeights = 0;
        for (int i = 1; i < curOrder.size(); i++) {
            sumWeights += curOrder.get(i).weight;
        }
        int risk = sumWeights - curOrder.get(0).strength;
        System.out.println(risk);
        out.append(risk + "\n");
        out.close();
        System.exit(0);
    }
    
    public static int getRisk(Cow bot, Cow top) {
        return top.weight - bot.strength;
    }
}
