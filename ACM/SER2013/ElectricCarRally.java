import java.util.*;

public class ElectricCarRally {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int stations = sc.nextInt();
            int roads = sc.nextInt();
            if (stations == 0 && roads == 0) return;
            Data[][] adj = new Data[stations][stations];
            for (int i = 0; i < roads; i++) {
                int u = sc.nextInt(), v = sc.nextInt();
                adj[u][v] = new Data();
                adj[v][u] = new Data();
                while (true) {
                    int from = sc.nextInt();
                    int to = sc.nextInt();
                    int cost = sc.nextInt();
                    Time t = new Time(from, to, cost);
                    adj[u][v].data.add(t);
                    adj[v][u].data.add(t);
                    if (to == 1439) break;//end time
                }
            }
            int[][] min = new int[stations][481];
	        for (int i = 0; i < stations; i++)
                Arrays.fill(min[i], Integer.MAX_VALUE);
            min[0][480] = 720;
            PriorityQueue<State> pq = new PriorityQueue<State>();
            pq.add(new State(0, 720, 2*4 * 60));
            while (!pq.isEmpty()) {
                State pop = pq.poll();
                if (pop.time > min[pop.station][pop.left]) continue;
                //System.out.println(pop);
                for (int i = 0; i < stations; i++) {
                    if (adj[i][pop.station] != null) {
                        int idx = -1;
                        for (Time t : adj[pop.station][i].data) {
                            idx++;
                            if (t.cost > 240) continue;
                            //try and adjust to this time and use it!
                            int delta = 0;
                            int curTime;
                            if (pop.left >= 2*t.cost) {
                                curTime = pop.time % 1440;
                            } else {
                                int needed = (2*t.cost-pop.left);//time needed to barely charge
                                delta = needed;
                                curTime = (pop.time+needed)%1440;
                            }
                            if (curTime >= t.from && curTime <= t.to) {
                                //trivial case...go
                                State newState = new State(i, pop.time + t.cost+delta, Math.min(480,pop.left + delta - 2*t.cost));
                                //System.out.println("NEWSTATE: " + newState + " FROM: " + idx);
                                if (newState.time < min[i][newState.left]) {
                                    min[i][newState.left] = newState.time;
                                    pq.add(newState);
                                }
                            } else if (curTime < t.from) {
                                int timeTill = t.from - curTime;
                                State newState = new State(i, pop.time + t.cost + delta + timeTill, Math.min(480, pop.left+delta+timeTill)-2*t.cost);
                               // System.out.println("NEWSTATE: " + newState + " FROM: " + idx);
                                if (newState.time < min[i][newState.left]) {
                                    min[i][newState.left] = newState.time;
                                    pq.add(newState);
                                }
                            } else if (curTime > t.to) {
                                int timeTill = t.from + 1440-curTime;
                                State newState = new State(i, pop.time + t.cost + delta + timeTill, Math.min(480, pop.left+delta+timeTill)-2*t.cost);
                                //System.out.println("NEWSTATE: " + newState + " FROM: " + idx);
                                if (newState.time < min[i][newState.left]) {
                                    min[i][newState.left] = newState.time;
                                    pq.add(newState);
                                }
                            }
                        }
                    }
                }
            }
            int minz = Integer.MAX_VALUE;
	        for (int i = 0; i <= 480; i++) minz = Math.min(minz, min[stations-1][i]);
	        System.out.println(minz-720);
        }
    }
}

class State implements Comparable<State> {
    public int station;
    public int time;
    public int left;

    public State(int station, int time, int left) {
        this.station = station;
        this.time = time;
        this.left = left;
    }

    public int compareTo(State st) {
        if (time == st.time) return st.left - left;
        return time - st.time;
    }

    public String toString() {
        return "STATE(" + station + "," + time + "," + left + ")";
    }
}

class Data {
    ArrayList<Time> data = new ArrayList<Time>();

    public int cost(int time) {
        time = time % 1439;
        for (Time t : data) {
            if (time >= t.from && time <= t.to) return t.cost;
        }
        return 0;//shouldn't happen.
    }
}

class Time {
    public int from, to;
    public int cost;

    public Time(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
