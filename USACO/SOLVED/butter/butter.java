
/*
ID: joshkir1
LANG: JAVA
TASK: butter
*/
import java.io.*;
import java.util.*;

public class butter {
    
    static LinkedList[] adjList;
    static int N, P, C;
    static int[] count;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("butter.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("butter.out"));
	String[] spl = sc.readLine().split(" ");
        N = Integer.parseInt(spl[0]);
	P = Integer.parseInt(spl[1]);
	C = Integer.parseInt(spl[2]);
	count = new int[P];
	for (int i = 0; i < N; i++) {
	    count[Integer.parseInt(sc.readLine())-1]++;
	}
	adjList = new LinkedList[P];
	for (int i = 0; i < P; i++) adjList[i] = new LinkedList<Pair>();
	for (int i = 0; i < C; i++) {
	    spl = sc.readLine().split(" ");
	    int u = Integer.parseInt(spl[0])-1, v = Integer.parseInt(spl[1])-1, w = Integer.parseInt(spl[2]);
	    adjList[u].add(new Pair(v, w));
	    adjList[v].add(new Pair(u, w));
	}
	int min = Integer.MAX_VALUE;
	for (int i = 0; i < P; i++) {
	    int sum = dijkstra(i);
	    min = Math.min(min, sum);
	}
	out.append(min+"\n");
        out.close();
    }
    static int dijkstra(int source) {
	int[] dist = new int[P];
	Arrays.fill(dist, Integer.MAX_VALUE);
	dist[source] = 0;
	PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
	pq.add(new Pair(source, 0));
	while (!pq.isEmpty()) {
	    Pair poll = pq.poll();
	    if (poll.weight > dist[poll.target]) continue;
	    for (Pair adj : (LinkedList<Pair>) adjList[poll.target]) {
		int sumDist = poll.weight + adj.weight;
		if (sumDist < dist[adj.target]) {
		    dist[adj.target] = sumDist;
		    pq.add(new Pair(adj.target, sumDist));
		}
	    }
	}
	int sum = 0;
	for (int i = 0; i < P; i++) {
	    sum += dist[i]*count[i];
	}
	return sum;
    }
    static class Pair implements Comparable<Pair> {
	public int target, weight;
	public Pair(int target, int weight) {
	    this.target = target;
	    this.weight = weight;
	}
	public int compareTo(Pair p) {
	    return weight - p.weight;
	}
    }
}
