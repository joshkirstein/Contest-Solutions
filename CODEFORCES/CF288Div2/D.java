import java.util.*;

public class D {
	static HashMap<String, ArrayList<String>> map;
	static HashMap<String, Integer> pos = new HashMap<String, Integer>();
	static HashSet<String> vis;
	static HashMap<String, Integer> in;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = Integer.parseInt(sc.nextLine());
		map = new HashMap<String, ArrayList<String>>();
		in = new HashMap<String, Integer>();
		vis = new HashSet<String>();
		for (int i = 0; i < N; i++) {
			String word = sc.nextLine();
			String pre = word.substring(0, 2);
			String post = word.substring(1, 3);
			if (!map.containsKey(pre)) {
				map.put(pre, new ArrayList<String>());
				pos.put(pre, 0);
			}
			if (!map.containsKey(post)) {
				map.put(post, new ArrayList<String>());
				pos.put(post, 0);
			}
			if (!in.containsKey(pre)) in.put(pre, 0);
			if (!in.containsKey(post)) in.put(post, 0);
			map.get(pre).add(post);
			in.put(post, in.get(post)+1);
		}
		int onediff = 0;
		String oneDiff = null;
		int negonediff = 0;
		String negOneDiff = null;
		int equaldiff = 0;

		String any = null;
		for (Map.Entry<String, ArrayList<String>> ent : map.entrySet()) {
			String node = ent.getKey();
			int outdeg = ent.getValue().size();
			int indeg = in.get(node);
			any = node;
			if (indeg == outdeg) equaldiff++;
			else if (outdeg-indeg == 1) {
				onediff++;
				oneDiff = node;
			} else if (outdeg-indeg == -1) {
				negonediff++;
				negOneDiff = node;
			}
		}
		if (onediff <= 1 && negonediff <= 1) {
			if (oneDiff != null) dfs(oneDiff);
			else if (negOneDiff != null) dfs(negOneDiff);
			else dfs(any);
			if (vis.size() != map.size()) {
				System.out.println("NO");
				return;
			}
			StringBuilder br = new StringBuilder("");
			for (int i = path.size()-1; i >= 0; i--) {
				if (i == path.size()-1) {
					br.append(path.get(i));
				} else {
					br.append(path.get(i).charAt(1));
				}
			}
			System.out.println("YES");
			System.out.println(br);
		} else {
			System.out.println("NO");
		}
	}
	static ArrayList<String> path = new ArrayList<String>();
	static void dfs(String current) {
		ArrayList<String> nodes = map.get(current);
		vis.add(current);
		int curPos = pos.get(current);
		if (nodes == null || curPos >= nodes.size()) {
			path.add(current);
		} else {
			while (!nodes.isEmpty()) {
				int p = pos.get(current);
				if (nodes == null || p >= nodes.size()) break;
				else {
					String next = nodes.get(p);
					pos.put(current, p+1);
					dfs(next);
				}
			}
			path.add(current);
		}
	}
}