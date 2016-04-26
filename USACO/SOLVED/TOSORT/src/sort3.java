/*
ID: joshkir1
LANG: JAVA
TASK: sort3
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class sort3 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("sort3.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("sort3.out"));
        int N = Integer.parseInt(sc.readLine());
        int[] arr = new int[N];
        int[] perf = new int[N];
        int[] sum = new int[4];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(sc.readLine());
            sum[arr[i]] += 1;
        }
        int idx = 0;
        for (int i = 1; i <= 3; i++) {
            int amt = sum[i];
            for (int j = 0; j < amt; j++) {
                perf[idx++] = i;
            }
        }
        int stage = 0;//0 = best waps, 1 = semi best, 2 = any.
        MAIN:
        while (true) {
            switch (stage) {
                case 0:
                    //System.out.println("0");
                    bestSwaps(arr, perf);
                    stage = 1;
                    continue MAIN;
                case 1:
                    //System.out.println("1");
                    boolean d = semiSwap(arr, perf);
                    if (!d) {
                        stage = 2;
                    } else {
                        stage = 0;
                    }
                    continue MAIN;
                case 2:
                    //System.out.println("2");
                    for (int i = 0; i < arr.length; i++) {
                        for (int j = i + 1; j < arr.length; j++) {
                            if (i != j) {
                                if (arr[i] > arr[j]) {
                                    swap(i, j, arr);
                                    stage = 0;
                                    continue MAIN;
                                }
                            }
                        }
                    }
                    break MAIN;
            }
        }
        System.out.println(count);
        out.append(count + "\n");
        out.close();
    }
    
    public static boolean semiSwap(int[] cur, int[] ideal) {
        for (int i = 0; i < cur.length; i++) {
            for (int j = 0; j < cur.length; j++) {
                if (i != j) {
                    if (cur[i] != ideal[i] && cur[j] != ideal[j]) {
                        if (cur[i] == ideal[j] || cur[j] == ideal[i]) {
                            System.out.println("SEMI");
                            swap(i, j, cur);
                            return true;
                        }
                    }
                }
            }
        }
        return false;//didn't do one
    }
    
    public static void bestSwaps(int[] cur, int[] ideal) {
        while (true) {
            boolean didOne = false;
            for (int i = 0; i < cur.length; i++) {
                for (int j = 0; j < cur.length; j++) {
                    if (i != j) {
                        if (cur[i] != ideal[i] && cur[j] != ideal[j]) {
                            if (cur[i] == ideal[j] && cur[j] == ideal[i]) {
                                System.out.println("BEST");
                                didOne = true;
                                swap(i, j, cur);
                            }
                        }
                    }
                }
            }
            if (!didOne) {
                return;
            }
        }
    }
    
    static int count = 0;
    
    public static void swap(int i, int j, int[] arr) {
        System.out.println("SWAP " + (i) + "," + (j));
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        count++;
    }
}