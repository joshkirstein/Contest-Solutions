
/*
ID: joshkir1
LANG: JAVA
TASK: msquare
*/
import java.io.*;
import java.util.*;

public class msquare {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader("msquare.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("msquare.out"));
	String perm = "";
	for (int i = 0; i < 8; i++) perm += "" + sc.nextInt();
	String start = "";
	for (int i = 1; i <= 8; i++) start += i + "";
	Queue<Transform> q = new LinkedList<Transform>();
	HashMap<String, Transform> set = new HashMap<String, Transform>();
	Transform begin = new Transform(null, start, '\0');
	q.add(begin);
	set.put(start, null);
	while (!q.isEmpty()) {
	    Transform tr = q.poll();
	    if (tr.to.equals(perm)) {
		StringBuilder br = new StringBuilder("");
		Transform cur = tr;
		while (cur != null) {
		    if (cur.from == null) break;
		    br.append(cur.trans + "");
		    cur = set.get(cur.from);
		}
		String cost = br.reverse().toString();
		out.append(cost.length() + "\n");
		if (cost.length() == 0) out.append("\n");
		int i = 0;
		while (true) {
		    if (i >= cost.length()) break;
		    int tempi = i+60;
		    out.append(cost.substring(i, Math.min(tempi, cost.length())) + "\n");
		    i = Math.min(tempi, cost.length());
		}
		out.close();
		return;
	    }
	    // 0 1 2 3 -> 7 6 5 4
	    // 7 6 5 4 -> 0 1 2 3
	    String transformA = tr.to.charAt(7)+""+tr.to.charAt(6)+""+tr.to.charAt(5)+""+tr.to.charAt(4)+""+tr.to.charAt(3)+""+tr.to.charAt(2)+""+tr.to.charAt(1)+""+tr.to.charAt(0);
	    if (!set.containsKey(transformA)) {
		Transform goA = new Transform(tr.to, transformA, 'A');
		q.add(goA);
		set.put(transformA, goA);
	    }
	    String transformB = tr.to.charAt(3)+""+tr.to.charAt(0)+""+tr.to.charAt(1)+""+tr.to.charAt(2)+""+tr.to.charAt(5)+""+tr.to.charAt(6)+""+tr.to.charAt(7)+""+tr.to.charAt(4);
	    if (!set.containsKey(transformB)) {
		Transform goB = new Transform(tr.to, transformB, 'B');
		q.add(goB);
		set.put(transformB, goB);
	    }
	    
	    String transformC = tr.to.charAt(0)+""+tr.to.charAt(6)+""+tr.to.charAt(1)+""+tr.to.charAt(3)+""+tr.to.charAt(4)+""+tr.to.charAt(2)+""+tr.to.charAt(5)+""+tr.to.charAt(7);
	    if (!set.containsKey(transformC)) {
		Transform goC = new Transform(tr.to, transformC, 'C');
		q.add(goC);
		set.put(transformC, goC);
	    }
	}
        out.close();
    }

    static class Transform {
	public String from, to;
	public char trans;
	public Transform(String from, String to, char trans) {
	    this.from = from;
	    this.to = to;
	    this.trans = trans;
	}
	public int hashCode() {
	    return to.hashCode();
	}
	public boolean equals(Object o) {
	    Transform t = (Transform) o;
	    return to.equals(t.to);
	}
    }
}
