package Section31;


/*
ID: joshkir1
LANG: JAVA
TASK: agrinet
*/
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class agrinet {
    
    static class UnionFind {
        public int[] root;
        public UnionFind(int size) {//union find, no path compression or heuristical stuff
            root = new int[size];
            for (int i = 0; i < size; i++) {
                root[i] = i;
            }
        }
        public void union(int u, int v) {
            int rU = find(u);
            int rV = find(v);
            root[rV] = rU;
        }
        public int find(int u) {//finds root...
            int rt = root[u];
            if (rt == u) return u;
            else return find(root[u]);
        }
        public boolean isConnected(int u, int v) {
            return find(u) == find(v);
        }
    }
    //kruskals:
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("agrinet.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("agrinet.out"));
        //input processing output here
        int N = sc.nextInt();
        UnionFind uf = new UnionFind(N);
        class Edge implements Comparable<Edge> {
            public int u, v, weight;
            public Edge(int u, int v, int weight) {
                this.u = u;
                this.v = v;
                this.weight = weight;
            }
            @Override
            public int compareTo(Edge o) {
                return weight - o.weight;
            }
            
        }
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int weight = sc.nextInt();
                if (weight > 0) {
                    edges.add(new Edge(i, j, weight));
                }
            }
        }
        Collections.sort(edges);
        int cost = 0;
        while (!edges.isEmpty()) {
            Edge e = edges.get(0);
            edges.remove(0);
            if (!uf.isConnected(e.u, e.v)) {
                uf.union(e.u, e.v);
                cost += e.weight;
            }
        }
        out.append(cost + "\n");
        out.close();
    }
}