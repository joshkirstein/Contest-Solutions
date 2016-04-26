package old;

/*
ID: joshkir1
LANG: JAVA
TASK: ride
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ride {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("ride.in")));
        PrintWriter out = new PrintWriter(new FileWriter("ride.out"));
        //Scanner sc = new Scanner(System.in);
        String cometName = sc.nextLine();
        String groupName = sc.nextLine();
        int cometProd = 1;
        int groupProd = 1;
        for (int i = 0; i < cometName.length(); i++) {
            cometProd *= ((int) (cometName.charAt(i))) - 64;
        }
        for (int i = 0; i < groupName.length(); i++) {
            groupProd *= ((int) (groupName.charAt(i))) - 64;
        }
        if ((cometProd % 47) == (groupProd % 47)) {
            out.println("GO");
            //System.out.println("GO");
        } else {
            out.println("STAY");
            //System.out.println("STAY");
        }
        out.close();
        System.exit(0);
    }
}
