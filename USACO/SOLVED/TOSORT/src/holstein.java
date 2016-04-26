/*
ID: joshkir1
LANG: JAVA
TASK: holstein
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class holstein {
    
    static class State2 {
        public int move;
        public int[] OLD;
        public ArrayList<Integer> used = new ArrayList<Integer>();
        public State2(int move, int[] OLD) {
            this.move = move;
            this.OLD = OLD;
        }
    }
    
    static class State {
        public int[] VIT;
        public State(int[] VIT) {
            this.VIT = VIT;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 61 * hash + Arrays.hashCode(this.VIT);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final State other = (State) obj;
            if (!Arrays.equals(this.VIT, other.VIT)) {
                return false;
            }
            return true;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("holstein.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("holstein.out"));
        //input processing output here
        int VIT = Integer.parseInt(sc.readLine());
        int[] vits = new int[VIT];
        String[] spl = sc.readLine().split(" ");
        for (int i = 0; i < spl.length; i++) {
            vits[i] = Integer.parseInt(spl[i]);
        }
        int SCOO = Integer.parseInt(sc.readLine());
        int[][] scoops = new int[SCOO][VIT];
        for (int i = 0; i < SCOO; i++) {
            String str = sc.readLine();
            while (str.contains("  ")) {
                str = str.replace("  ", " ");
            }
            spl = str.split(" ");
            for (int j = 0; j < spl.length; j++) {
                scoops[i][j] = Integer.parseInt(spl[j]);
            }
        }
        int[] goal = new int[VIT];
        Queue<State> q = new LinkedList<State>();
        HashMap<State, State2> map = new HashMap<State, State2>();
        State init = new State(vits);
        q.add(init);
        map.put(init, null);//integer is previous scoup 
        while (!q.isEmpty()) {
            State pop = q.poll();
            //System.out.println(Arrays.toString(pop.VIT));
            State2 poz = map.get(pop);
            if (done(pop.VIT)) {
                System.out.println("sup");
                Stack<Integer> stk = new Stack<Integer>();
                State2 cur = map.get(pop);
                while (cur != null) {
                    stk.push(cur.move);
                    cur = map.get(new State(cur.OLD));
                }
                out.append(stk.size() + "");
                while (!stk.isEmpty()) {
                    out.append(" " + (stk.pop() + 1));
                }
                out.append("\n");
                break;
            } else {
                LOOP:
                for (int i = 0; i < SCOO; i++) {
                    if (poz != null && poz.used.contains(i)) continue;
                    //System.out.println("Try");
                    //Try all possible scoops.
                    int[] next = new int[VIT];
                    for (int j = 0; j < VIT; j++) {
                        next[j] = pop.VIT[j] - scoops[i][j];
                    }
                    //Possible if we're here!
                    State adj = new State(next);
                    if (!map.containsKey(adj)) {
                        //System.out.println("add " + i);
                        q.add(adj);
                        State2 nextStt = new State2(i, pop.VIT);
                        if (poz != null)
                            nextStt.used.addAll(poz.used);
                        nextStt.used.add(i);
                        map.put(adj, nextStt);
                    }
                }
            }
        }
        out.close();
    }
    
    public static boolean done(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                return false;
            }
        }
        return true;
    }
}