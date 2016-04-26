import java.util.*;

class G {
	static String start, end;
	static String[] words;
	static int n, len;
	static ArrayList<ArrayList<Integer>> oneAwayList;
	static ArrayList<ArrayList<Integer>> twoAwayList;
	static int[] distFromStart;
	static int[] distFromEnd;
	static final int BIG = Integer.MAX_VALUE / 3;
	public static void main (String [] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		words = new String[n];
		start = sc.next();
		len = start.length();
		end = sc.next();
		words[0] = start;
		words[1] = end;
		for (int i = 2; i < n; i++)
			words[i] = sc.next();
		if (start.equals(end)) {
			System.out.println(0 + "\n" + 0);
			return;
		} else if(len == 1) {
			System.out.println(0 + "\n" + 1);
			return;
		}
		createLists();
		distFromStart = new int[n];
		distFromEnd = new int[n];
		Arrays.fill(distFromStart, BIG);
		Arrays.fill(distFromEnd, BIG);
		
		bfs(0, true, 0);
		bfs(1, false, 1); // because of 1-indexing bullshit
		// System.out.println(Arrays.toString(distFromStart));
		int minDist = BIG - 1;
		String bridgeWord = "";
		// System.out.println(Arrays.toString(distFromEnd));
		
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> neighbors = twoAwayList.get(i);
			// System.out.println(i + "'s neighbors: " + neighbors);
			for (int j : neighbors) {
				int sum = distFromStart[i] + distFromEnd[j]; //handles double-counting
				
				if (sum > minDist)
					continue;
				// System.out.println(i + " " + j);
				char[] combination = new char[len];
				int grabbedFrom = -1;
				char a, b;
				for (int k = 0; k < len; k++) {
					if (words[i].charAt(k) == words[j].charAt(k))
						combination[k] = words[i].charAt(k);
					else {
						/*if (!alreadyGrabbedFromA) {
							a = words[i].charAt(k);
						} else {
							b = words[j].charAt(k);
						}
						 = (alreadyGrabbedFromA ? (words[i].charAt(k) < words[j].charAt(k)));
						alreadyGrabbedFromA = true;*/
						a = words[i].charAt(k);
						b = words[j].charAt(k);
						if (grabbedFrom == -1) {
							a = words[i].charAt(k);
							b = words[j].charAt(k);
							if (a < b) {
								combination[k] = a;
								grabbedFrom = 0;
							}
							else {
								combination[k] = b;
								grabbedFrom = 1;
							}
						} else if (grabbedFrom == 0) {
							combination[k] = b;
						} else {
							combination[k] = a;
						}
						

					}
				}
				String newBridgeWord = new String(combination);
				// System.out.println(newBridgeWord);
				if (sum < minDist || (sum == minDist && newBridgeWord.compareTo(bridgeWord) < 0)) {
					minDist = sum;
					bridgeWord = newBridgeWord;
				} 
			}
		}
		minDist++; //BECAUSE WE GOT IT FROM ADDINGA  WORD!
		
		if (minDist >= distFromStart[1]) {
			System.out.println(0);
			if (minDist == BIG)
				System.out.println(-1);
			else
				System.out.println(distFromStart[1]);
		} else {
			System.out.println(bridgeWord);
			System.out.println(minDist);
		}
		
		/*
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < twoAwayList.get(i).size(); j++) {
				System.out.print(twoAwayList.get(i).get(j) + " ");
			}
			System.out.println();
		}
		*/
		
	}
	static class Node {
		int id;
		int depth;
		Node(int a, int b) {
			id = a;
			depth = b;
		}
	}
	static void bfs(int base, boolean fromStart, int depth) {
		Node n = new Node(base, depth);
		int[] dist = (fromStart ? distFromStart : distFromEnd);
		dist[base] = depth;
		LinkedList<Node> q = new LinkedList<Node>();
		q.offer(n);
		while(!q.isEmpty()) {
			n = q.poll();
			ArrayList<Integer> neighbors = oneAwayList.get(n.id);
			for (Integer i : neighbors) {
				if(dist[i] == BIG) {
					dist[i] = n.depth + 1;
					q.offer(new Node(i, n.depth+1));
				}
			}
		}
	}
	static void createLists() { // bi-directional
		oneAwayList = new ArrayList<ArrayList<Integer>>();
		twoAwayList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			oneAwayList.add(new ArrayList<Integer>());
			twoAwayList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int numDiff = 0;
				for (int k = 0; k < len; k++) {
					if (words[i].charAt(k) != words[j].charAt(k))
						numDiff++;
				}
				if (numDiff == 1) {
					oneAwayList.get(i).add(j);
					oneAwayList.get(j).add(i);
				} else if (numDiff == 2) {
					twoAwayList.get(i).add(j);
					twoAwayList.get(j).add(i);
				}
			}
		}
	}
}
