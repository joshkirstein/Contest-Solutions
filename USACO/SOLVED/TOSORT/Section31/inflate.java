package Section31;


/*
ID: joshkir1
LANG: JAVA
TASK: inflate
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class inflate {
    
    //unbounded knapsack problem...time is weight, points is money 
    //M[w] = max costs using weight <= w
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("inflate.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("inflate.out"));
        //input processing output here
        StringTokenizer str = new StringTokenizer(sc.readLine());
        String[] spl = null;
        int total = Integer.parseInt(str.nextToken()), categories = Integer.parseInt(str.nextToken());
        class Item {
            public int cost, weight;
        }
        Item[] items = new Item[categories];
        for (int i = 0; i < categories; i++) {
            str = new StringTokenizer(sc.readLine());
            int cost = Integer.parseInt(str.nextToken()), weight = Integer.parseInt(str.nextToken());
            Item item = new Item();
            item.cost = cost;
            item.weight = weight;
            items[i] = item;
        }
        int[] M = new int[total + 1];
        int max = 0;
        /*
        for (int w = 0; w <= total; w++) {
            for (int i = 0; i < categories; i++) {
                if (w >= items[i].weight) {
                    M[w] = Math.max(M[w], M[w - items[i].weight] + items[i].cost);
                }
                max = Math.max(max, M[w]);
            }
        }*/
        for (int i = 0; i < categories; i++) {
            for (int w = items[i].weight; w <= total; w++) {
                //going from 0...total allows for unbounded knapsack
                //since it allows for the possibility of previous inclusion
                //going from total...0 doesn't and therefore is suitable
                //for 0-1 knapsack
                M[w] = Math.max(M[w], M[w - items[i].weight] + items[i].cost);
                max = Math.max(M[w], max);
            }
        }
        //System.out.println(max);    
        out.append(max + "\n");
        out.close();
    }
}