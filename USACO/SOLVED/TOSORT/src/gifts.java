/*
ID: joshkir1
LANG: JAVA
TASK: gifts
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class gifts {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int x = 0;
        for (int i = 51; i <= 100; i++) {
            x += 7*i;
        }
        System.out.println(x);
        BufferedReader sc = new BufferedReader(new FileReader("gifts.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("gifts.out"));
        String[] inp = sc.readLine().split(" ");
        int cows = Integer.parseInt(inp[0]);
        int budget = Integer.parseInt(inp[1]);
        int res = 0;
        class Data { 
            int p, s;
            public Data(int p, int s) {
                this.p = p;
                this.s = s;
            }
            public int get(int budg) {
                return budg - (p + s);
            }
            public int gethalf(int budg) {
                return budg - (p / 2 + s);
            }
            public boolean canget(int budg) {
                return (budg) >= (p + s);
            }
            public boolean cangethalf(int budg) {
                return (budg) >= (p / 2 + s);
            }
            public String toString() {
                return "pi: " + p + " si: " + s;
            }
        }
        PriorityQueue<Data> costs = new PriorityQueue<Data>(10, new Comparator<Data>() {
            public int compare(Data one, Data two) {
                if ((one.p + one.s) == (two.p + two.s)) {
                    if (one.p > two.p) {
                        return 0;//get with p greater in front
                    } else {
                        return 1;
                    }
                }
                return (one.p + one.s) - (two.p + two.s);
            }
        });
        for (int i = 0; i < cows; i++) {
            String[] cow = sc.readLine().split(" ");
            int pi = Integer.parseInt(cow[0]);
            int si = Integer.parseInt(cow[1]);
            costs.add(new Data(pi, si));
        }
        //System.out.println(costs);
        boolean usedHalf = false;
        while (!costs.isEmpty() && budget >= 0) {
            Data pop = costs.poll();
            if (pop.canget(budget)) {
                Data peek = costs.peek();
                if (peek != null && !peek.canget(pop.get(budget)) && !usedHalf) {
                    //System.out.println("Can't get " + peek + " without halving " + pop);
                    if (peek.canget(pop.gethalf(budget))) {
                        //System.out.println("Getting halff price " + pop);
                        budget = pop.gethalf(budget);
                        res++;
                        usedHalf = true;
                    } else {
                        break;//used half cant go any further, done.
                    }
                } else {
                    //System.out.println("Getting full price " + pop);
                    budget = pop.get(budget);
                    res++;
                }
            } else {
                if (!usedHalf) {
                    if (pop.cangethalf(budget)) {
                        //System.out.println("Getting half price " + pop);
                        budget = pop.gethalf(budget);
                        res++;
                        usedHalf = true;
                    } else {
                        break;//not enough, done.
                    }
                } else {
                    break;
                }
            }
        }
        out.append(res + "");
        out.newLine();
        out.close();
        //System.exit(0);
    }
}
