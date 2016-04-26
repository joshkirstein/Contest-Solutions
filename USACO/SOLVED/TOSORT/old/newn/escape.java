package old.newn;

/*
ID: joshkir1
LANG: JAVA
TASK: escape
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class escape {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("escape.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("escape.out"));
        int numCows = Integer.parseInt(sc.readLine());
        long[] weights = new long[numCows];
        for (int i = 0; i < numCows; i++) {
            weights[i] = Long.parseLong(sc.readLine());
        }
        out.append(getMax(0, 0, 0, weights) + "");
        out.append("\n");
        out.close();
        System.exit(0);
    }
    
    public static int getMax(int curIdx, int count, long last, long[] weights) {
        if (curIdx >= weights.length) return count;
        //Test for carry if can't return count..
        if (last != 0) {
            String strLast = String.valueOf(last);
            String strCur = String.valueOf(weights[curIdx]);
            int maxLength = Math.max(strLast.length(), strCur.length());
            strLast = String.format(("%" + maxLength + "d"), last);
            strCur = String.format(("%" + maxLength + "d"), weights[curIdx]);
            for (int i = 0; i < strLast.length(); i++) {
                char lastCh = strLast.charAt(i);
                char curCh = strCur.charAt(i);
                if (!Character.isWhitespace(lastCh) && !Character.isWhitespace(curCh)) {
                    int iLast = Integer.parseInt(lastCh + "");
                    int iCur = Integer.parseInt(curCh + "");
                    if ((iLast + iCur) >= 10) {
                        //System.out.println(strLast + " AND " + strCur + " HAVE CARRYOVER!");
                        //carry!
                        return Math.max(count, getMax(curIdx + 1, count, last, weights));//attempt to not take this one also..
                    }
                }
            }
        }
        int max = 0;
        max = Math.max(max, getMax(curIdx + 1, count + 1, last + weights[curIdx], weights));//Take weights[curIdx]
        max = Math.max(max, getMax(curIdx + 1, count, last, weights));//Dont take weights[curIdx]
        return max;
    }
}
