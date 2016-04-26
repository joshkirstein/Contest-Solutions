package old;



/*
ID: joshkir1
LANG: JAVA
TASK: acrobat
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class acrobat {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader(new File("acrobat.in")));
        PrintWriter out = new PrintWriter(new FileWriter("acrobat.out"));
        int amt = Integer.parseInt(sc.nextLine());
        Cow[] cows = new Cow[amt];
        for (int i = 0; i < amt; i++) {
            String[] spl = sc.nextLine().split(" ");
            cows[i] = new Cow(Integer.parseInt(spl[0]), Integer.parseInt(spl[1]));
        }
        final ArrayList<Cow> curStack = new ArrayList<Cow>();
        for (int i = 0; i < amt; i++) {
            Arrays.sort(cows, new Comparator<Cow>() {

                public int compare(Cow o1, Cow o2) {
                    int risk1 = getRisk(o1, curStack);
                    int risk2 = getRisk(o2, curStack);
                    return risk1 - risk2;
                }

            });
            for (int j = 0; j < cows.length; j++) {
                if (cows[j] != null) {
                    curStack.add(cows[j]);
                    cows[j] = null;
                    break;
                }
            }
        }
        int max = 0;
        for (Cow c : curStack) {
            max = Math.max(max, getRisk(c, curStack));
           //out.println("(" + c.weight + ", " + c.strength + ") RISK: " + getRisk(c, curStack));
        }
        out.println(max);
        out.close();
        System.exit(0);
    }
    
    public static int getRisk(Cow cow, ArrayList<Cow> stack) {
        if (cow == null) {
            return 1000000;
        }
        if (stack.size() == 0) {
            return cow.strength;
        } else {
            if (!stack.contains(cow)) {
                return cow.strength;
            }
            int idx = stack.indexOf(cow);
            int weightComb = 0;
            for (int i = idx + 1; i < stack.size(); i++) {
                weightComb += stack.get(i).weight;
            }
            if (weightComb == 0) {
                return 0;
            }
            return Math.abs(cow.strength - weightComb);
        }
    }
    
    static class Cow {
        public int weight, strength;
        public Cow(int weight, int strength) {
            this.weight = weight;
            this.strength = strength;
        }
        public boolean equals(Object o) {
            if (o instanceof Cow) {
                Cow c = (Cow) o;
                return c.weight == weight && c.strength == strength;
            }
            return false;
        }
    }
}