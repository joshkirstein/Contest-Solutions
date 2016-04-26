package Section31;


/*
ID: joshkir1
LANG: JAVA
TASK: contact
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class contact {
    
    //static int[] freq = new int[(int) Math.pow(2, 12)];
    //literally find each bit pattern
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("contact.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("contact.out"));
        //input processing output here
        String[] pl = sc.readLine().split(" ");
        int A = Integer.parseInt(pl[0]), B = Integer.parseInt(pl[1]), N = Integer.parseInt(pl[2]);
        StringBuilder br = new StringBuilder("");
        String s = null;
        while ((s = sc.readLine()) != null) {
            if (s.endsWith(" ")) {
                br.append(s.substring(0, s.length()-1));
            } else {
                br.append(s);
            }
        }
        //Intead of freq array, use freq map mapping string pattern to integer frequency-.-.-.-
        Map<String, Integer> freq = new HashMap<String, Integer>();
        for (int i = 0; i < br.length(); i++) {
            for (int j = A; j <= B; j++) {
                try {
                String sub = br.substring(i, i + j);
                //int in = Integer.parseInt(sub, 2);
                if (freq.containsKey(sub)) {
                    freq.put(sub, freq.get(sub) + 1);
                } else {
                    freq.put(sub, 1);
                }
                //System.out.println("SUB: " + sub);
                } catch (Exception ex) { break; }
                
            }
        }
        TreeMap<Integer, ArrayList<String>> freqTree = new TreeMap<Integer, ArrayList<String>>();
        for (Map.Entry<String, Integer> ent : freq.entrySet()) {
            if (freqTree.containsKey(ent.getValue())) {
                freqTree.get(ent.getValue()).add(ent.getKey());
            } else {
                ArrayList<String> in = new ArrayList<String>();
                in.add(ent.getKey());
                freqTree.put(ent.getValue(), in);
            }
        }
        int amt = 0;
        for (Map.Entry<Integer, ArrayList<String>> ent : freqTree.descendingMap().entrySet()) {
            out.append(ent.getKey() + "\n");
            Collections.sort(ent.getValue(), new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    if (o1.length() == o2.length()) {
                        int iVal = Integer.parseInt(o1, 2);
                        int uVal = Integer.parseInt(o2, 2);
                        return iVal - uVal;
                    } else {
                        return o1.length() - o2.length();
                    }
                }
                
            });
            boolean start = true;
            int amtPer = 0;
            for (int i = 0; i < ent.getValue().size(); i++) {//print six per line...
                String iVal = ent.getValue().get(i);
                if (start) {
                    out.append(iVal);
                    start = false;
                } else {
                    out.append(" " + iVal);
                }
                amtPer++;
                if (amtPer == 6 && i != ent.getValue().size() - 1) {
                    out.append("\n");
                    amtPer = 0;
                    start = true;
                }
            }
            out.append("\n");
            amt++;
            if (amt >= N) break;
        }
        out.close();
    }
}