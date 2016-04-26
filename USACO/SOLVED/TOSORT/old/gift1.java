package old;

/*
ID: joshkir1
LANG: JAVA
TASK: gift1
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class gift1 {

    public static void main(String[] args) throws IOException {
        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader(new File("gift1.in")));
        PrintWriter out = new PrintWriter(new FileWriter("gift1.out"));
        sc.useDelimiter("\n");
        int NP = Integer.parseInt(sc.nextLine());
        List<String> order = new ArrayList<String>();
        Map<String, Integer> personMap = new HashMap<String, Integer>();
        Map<String, Integer> initMap = new HashMap<String, Integer>();
        for (int i = 0; i < NP; i++) {
            String name = sc.nextLine();
            personMap.put(name, 0);
            order.add(name);
        }
        while (sc.hasNextLine()) {
            String giver = sc.nextLine();
            if (giver.equals("")) {
                break;
            }
            String[] info = sc.nextLine().split(" ");
            int moneyToGive = Integer.parseInt(info[0]);
            int peopleToGive = Integer.parseInt(info[1]);
            if (!initMap.containsKey(giver)) {
                initMap.put(giver, moneyToGive);
            }
            if (peopleToGive == 0) {
                continue;
            }
            int moneyPer = moneyToGive / peopleToGive;
            int moneyLeft = moneyToGive - (moneyPer * peopleToGive);
            personMap.put(giver, personMap.get(giver) + moneyLeft);//Add the left amount of money to giver
            for (int i = 0; i < peopleToGive; i++) {
                String toGive = sc.nextLine();
                personMap.put(toGive, personMap.get(toGive) + moneyPer);
            }
        }
        for (String name : order) {
            out.println(name + " " + (personMap.get(name) - initMap.get(name)));
            //System.out.println(name + " " + (personMap.get(name) - initMap.get(name)));
        }
        out.close();
    }
}