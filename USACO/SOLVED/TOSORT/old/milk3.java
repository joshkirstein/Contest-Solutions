package old;

/*
ID: joshkir1
LANG: JAVA
TASK: milk3
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class milk3 {

    static boolean[] OUTPUT = new boolean[21];
    static int A, B, C;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader(new File("milk3.in")));
        PrintWriter out = new PrintWriter(new FileWriter("milk3.out"));
        String[] spl = sc.readLine().split(" ");
        A = Integer.parseInt(spl[0]);
        B = Integer.parseInt(spl[1]);
        C = Integer.parseInt(spl[2]);
        run(0, 0, C, new boolean[21][21][21]);
        String output = "";
        for (int i = 0; i < OUTPUT.length; i++) {
            if (OUTPUT[i]) {
                output += i + " ";
            }
        }
        out.println(output.trim());
        out.close();
    }

    public static void run(int curA, int curB, int curC, boolean[][][] visited) {
        if (curA == 0) {
            OUTPUT[curC] = true;
        }
        if (curA != 0) {//then a to b, a to c
            int spaceB = B - curB;
            if (spaceB >= curA) {
                int nextA = 0;
                int nextB = curB + curA;
                int nextC = curC;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = curA - spaceB;
                int nextB = B;//Fill
                int nextC = curC;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
            int spaceC = C - curC;
            if (spaceC >= curA) {
                int nextA = 0;
                int nextB = curB;
                int nextC = curC + curA;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = curA - spaceC;
                int nextB = curB;
                int nextC = C;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
        }
        if (curB != 0) {//then b to a, b to c
            int spaceA = A - curA;
            if (spaceA >= curB) {
                int nextA = curA + curB;
                int nextB = 0;
                int nextC = curC;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = A;//Fill
                int nextB = curB - spaceA;
                int nextC = curC;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
            int spaceC = C - curC;
            if (spaceC >= curB) {
                int nextA = curA;
                int nextB = 0;
                int nextC = curC + curB;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = curA;
                int nextB = curB - spaceC;
                int nextC = C;//fill
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
        }
        if (curC != 0) {//then c to a, c to b
            int spaceA = A - curA;
            if (spaceA >= curC) {
                int nextA = curA + curC;
                int nextB = curB;
                int nextC = 0;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = A;//Fill
                int nextB = curB;
                int nextC = curC - spaceA;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
            int spaceB = B - curB;
            if (spaceB >= curC) {
                int nextA = curA;
                int nextB = curB + curC;
                int nextC = 0;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            } else {
                int nextA = curA;
                int nextB = B;//fill
                int nextC = curC - spaceB;
                if (!visited[nextA][nextB][nextC]) {
                    visited[nextA][nextB][nextC] = true;
                    run(nextA, nextB, nextC, visited);
                }
            }
        }
    }
}