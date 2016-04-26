/*
ID: joshkir1
LANG: JAVA
TASK: race3
*/
import java.io.*;
import java.util.*;
import java.math.*;
class race3
{
	static HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(new FileInputStream("race3.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("race3.out")));

		int i = 0;
		while (true) {
			String str = sc.nextLine();
			if (str.equals("-1")) break;
			ArrayList<Integer> test = new ArrayList<Integer>();
			map.put(i, test);
			String[] spl = str.split(" ");
			for (int j = 0; j < spl.length-1; j++) {
				int to = Integer.parseInt(spl[j]);
				test.add(to);
			}
			i++;
		}

		for (i = 1; i < map.size(); i++) {
			vis = new boolean[map.size()];
			dfs(0, i);
			boolean works = true;
			for (int j = 0; j < map.size(); j++) {
				if (!vis[j] && j != i) {
					works = false;
				}
			}
			if (!works) {
				artic.add(i);
			}
		}
		TreeSet<Integer> good = new TreeSet<Integer>();
		vis = new boolean[map.size()];
		dfs(0, -1);
		for (i = 0; i < map.size(); i++) {
			if (vis[i]) {
				if (artic.contains(i)) 
					good.add(i);
			}
		}
		out.print(good.size());
		for (Integer g : good) {
			out.print(" " + g);
		}

		out.println();
		TreeSet<Integer>ogood=new TreeSet<Integer>();
		for (Integer disc : good) {
			vis = new boolean[map.size()];
			vis2 = null;
			flag = false;
			dfs(0, disc);
			vis2 = vis;
			vis = new boolean[map.size()];
			dfs(disc, disc);
			if (!flag) {
				ogood.add(disc);
			}
		}
		out.print(ogood.size());
		for (Integer og : ogood) {
			out.print(" " + og);
		}
		out.println();
		out.close();
		System.exit(0);
	}
	static TreeSet<Integer> artic = new TreeSet<Integer>();
	static boolean[] vis;
	static boolean flag = false;
	static boolean[] vis2;
	static void dfs(int cur, int ignore) {
		vis[cur] = true;
		ArrayList<Integer> next = map.get(cur);
		for (Integer i : next) {
			if (vis2 != null && vis2[i] && i != ignore) {
				flag = true;
			}
			if (i == ignore) continue;
			if (vis[i]) continue;
			dfs(i, ignore);
		}
	}
}