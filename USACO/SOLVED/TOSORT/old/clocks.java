/*
ID: joshkir1
LANG: JAVA
TASK: clocks
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class clocks {
    
    static Extra NULL = new Extra(null, -1, 0);
    
     static class Extra {

        private ClockSet from;
        private int move;
        private int step;

        public Extra(ClockSet from, int move, int step) {
            this.from = from;
            this.move = move;
            this.step = step;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long ms = System.currentTimeMillis();
        BufferedReader sc = new BufferedReader(new FileReader("clocks.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("clocks.out"));
        String[][] s = new String[3][];
        s[0] = sc.readLine().split(" ");
        s[1] = sc.readLine().split(" ");
        s[2] = sc.readLine().split(" ");
        int[] startArr = new int[]{
            Integer.parseInt(s[0][0]), Integer.parseInt(s[0][1]), Integer.parseInt(s[0][2]),
            Integer.parseInt(s[1][0]), Integer.parseInt(s[1][1]), Integer.parseInt(s[1][2]),
            Integer.parseInt(s[2][0]), Integer.parseInt(s[2][1]), Integer.parseInt(s[2][2])
        };
        ClockSet start = new ClockSet(startArr);
        ClockSet goal = new ClockSet(new int[] {12, 12, 12, 12, 12, 12, 12, 12, 12});
        Queue<ClockSet> q = new LinkedList<ClockSet>();
        Extra[] pred = new Extra[262146];
        q.add(start);
        pred[start.getBitmask()] = NULL;
        while (!q.isEmpty()) {
            ClockSet cl = q.poll();
            Extra z = pred[cl.getBitmask()];
            if (z.step + 1 >= 19) continue;//guess :(
            if (cl.getBitmask() == goal.getBitmask()) {
                StringBuffer moves = new StringBuffer("");
                ClockSet cur = goal;
                boolean first = true;
                while (true) {
                    Extra move = pred[cur.getBitmask()];
                    if (move == NULL) break;
                    if (first) {
                        moves.append(move.move);
                        first = false;
                    } else {
                        moves.insert(0, " ");
                        moves.insert(0, move.move);
                    }
                    cur = move.from;
                }
                moves.append("\n");
                out.append(moves);
                out.close();
                long delta = System.currentTimeMillis() - ms;
                System.out.println(delta + "ms");
                System.exit(0);
            } else {
                for (int i = 1; i <= 9; i++) {
                    ClockSet next = cl.move(i);
                    if (pred[next.getBitmask()] == null) {
                        q.add(next);
                        pred[next.getBitmask()] = new Extra(cl, i, z.step + 1);
                    }
                }
            }
        }
        System.exit(0);
    }

    static class ClockSet {

        private int[] set;
        private int bitmask;

        public ClockSet(int[] set) {
            this.set = set;
            this.bitmask = getBitmaskInt();
        }

        public ClockSet move(int move) {
            switch (move) {
                case 1:
                    return new ClockSet(new int[]{getRotate90(set[0]), getRotate90(set[1]), set[2],  getRotate90(set[3]), getRotate90(set[4]), set[5], set[6], set[7], set[8]});
                case 2:
                    return new ClockSet(new int[]{getRotate90(set[0]), getRotate90(set[1]), getRotate90(set[2]), set[3], set[4], set[5], set[6], set[7], set[8]});
                case 3:
                    return new ClockSet(new int[]{set[0], getRotate90(set[1]), getRotate90(set[2]), set[3], getRotate90(set[4]), getRotate90(set[5]), set[6], set[7], set[8]});
                case 4:
                    return new ClockSet(new int[]{getRotate90(set[0]), set[1], set[2], getRotate90(set[3]), set[4], set[5], getRotate90(set[6]), set[7], set[8]});
                case 5:
                    return new ClockSet(new int[]{set[0], getRotate90(set[1]), set[2], getRotate90(set[3]), getRotate90(set[4]), getRotate90(set[5]), set[6], getRotate90(set[7]), set[8]});
                case 6:
                    return new ClockSet(new int[]{set[0], set[1], getRotate90(set[2]), set[3], set[4], getRotate90(set[5]), set[6], set[7], getRotate90(set[8])});
                case 7:
                    return new ClockSet(new int[]{set[0], set[1], set[2], getRotate90(set[3]), getRotate90(set[4]), set[5], getRotate90(set[6]), getRotate90(set[7]), set[8]});
                case 8:
                    return new ClockSet(new int[]{set[0], set[1], set[2], set[3], set[4], set[5], getRotate90(set[6]), getRotate90(set[7]), getRotate90(set[8])});
                case 9:
                    return new ClockSet(new int[]{set[0], set[1], set[2], set[3], getRotate90(set[4]), getRotate90(set[5]), set[6], getRotate90(set[7]), getRotate90(set[8])});
            }
            return null;
        }
        
        public int getBitmask() {
            return bitmask;
        }
        
        private int getBitmaskInt() {
            int num = 0;//all bits off
            int cur = 0;//cur digit we are on
            for (int i = 0; i < 18; i += 2, cur++) {
                switch (set[cur]) {
                    case 3:
                        //Leave it alone, 0 0
                        break;
                    case 6:
                        num |= 1 << i + 1;//0 1
                        break;
                    case 9:
                        num |= 1 << i;//1 0
                        break;
                    case 12:
                        num |= 1 << i;//1 1
                        num |= 1 << i + 1;
                        break;
                }
            }
            return num;//base 2 :)
        }
        
        public static int getRotate90(int pos) {
            int temp;
            if (pos == 12) {
                temp = 3;
            } else {
                temp = pos + 3;
            }
            return temp;
        }
    }
}