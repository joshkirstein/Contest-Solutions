import java.util.*;

public class RacingGems {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long R = sc.nextInt();
		int width = sc.nextInt();
		int height = sc.nextInt();
		Pt[] pts = new Pt[N];
		TreeSet<Long> ys = new TreeSet<Long>();
		HashMap<Long, Integer> y_idx_map = new HashMap<Long, Integer>();
		for (int i = 0; i < N; i++) {
			long x = R*sc.nextInt();//45 degrees expansion
			long y = sc.nextInt();
			pts[i] = new Pt(x+y, y-x);//rotate 45 degrees clockwise
			ys.add(y-x);
		}
		int idx = 0;
		for (Long ll : ys) {
			//System.out.println("Y: " + ll + " GETS: " + idx);
			y_idx_map.put(ll, idx++);
		}
		Arrays.sort(pts);//sort by x, y doesnt really matter
		int best = 1;//ans
		SegmentTree st = new SegmentTree(new int[ys.size()]);
		for (int i = N-1; i >= 0; i--) {
			int index = y_idx_map.get(pts[i].y);
			//System.out.println("INDEX: " + index);
			int bstHere = Math.max(1, st.query(index, ys.size()-1)+1);
			int cur = st.query(index, index);
			if (cur < bstHere)
				st.update(bstHere, index, index);

			best = Math.max(best, bstHere);
		}
		System.out.println(best);
	}
}
class Pt implements Comparable<Pt> {
	long x, y;
	public Pt(long x, long y) {
		this.x = x;
		this.y = y;
	}
	public int compareTo(Pt other) {
		if (x < other.x) return -1;
		if (x > other.x) return 1;
		if (y < other.y) return -1;
		if (y > other.y) return 1;
		return 0;
	}
}
class SegmentTree {
	//This segment tree is for RMQ (Range Maximum Queries).
	//Answers what is largest element from [lo, hi] in log(sz) time
	//Note: s and e represent range of current segment we're looking at
	//[s, e]. [lo, hi] represent the subsequence in the physical array given.

	public int[] tree;
	public int[] lazy;
	public int size;

	public SegmentTree(int[] array) {
		size = array.length;
		tree = new int[4*size];
		lazy = new int[4*size];
		build(0, 0, array.length-1, array);
	}	

	//finds the maximum elements between [low, high]
	public int query(int low, int high) {
		return query(0, low, high, 0, size-1);
	}

	//updates [low, hi] with val
	//note if val < elements in [low, hi]
	//we prefer to take the larger elements over val.
	public void update(int val, int low, int high) {
		update(0, low, high, 0, size - 1, val);
	}

	private void build(int idx, int s, int e, int[] array) {
		if (s == e) {
			//Segment covers 1 element
			tree[idx] = array[s];
		} else {
			build(left(idx), s, (s+e)/2, array);
			build(right(idx), (s+e)/2+1, e, array);
			tree[idx] = Math.max(tree[left(idx)], tree[right(idx)]);
		}
	}

	private void update(int idx, int lo, int hi, int s, int e, int val) {
		if (hi < lo) return;
		lazy(idx, s, e);
		if (s > hi || e < lo) return; //not part of range
		if (s >= lo && e <= hi) {
			tree[idx] = Math.max(tree[idx], val);
			if (s != e) {
				lazy[left(idx)] = Math.max(val, lazy[left(idx)]);
				lazy[right(idx)] = Math.max(val, lazy[right(idx)]);
			}
			return;
		}
		update(left(idx), lo, hi, s, (s+e)/2, val);
		update(right(idx), lo, hi, (s+e)/2+1, e, val);
		tree[idx] = Math.max(tree[idx], tree[left(idx)]);
		tree[idx] = Math.max(tree[idx], tree[right(idx)]);
	}

	private int query(int idx, int lo, int hi, int s, int e) {
		if (hi < lo) return 0;
		if (s > hi || e < lo) return 0;
		lazy(idx, s, e);
		if (s >= lo && hi >= e) return tree[idx];
		else {
			int queryL = query(left(idx), lo, hi, s, (s+e)/2);
			int queryR = query(right(idx), lo, hi, (s+e)/2+1, e);
			return Math.max(queryL, queryR);
		}
	}

	//helper function for lazy propagation
	private void lazy(int idx, int s, int e) {
		if (lazy[idx] != 0) {
			if (lazy[idx] > tree[idx]) {
				tree[idx] = lazy[idx];
			}
			if (s != e) {
				lazy[left(idx)] = Math.max(lazy[idx], lazy[left(idx)]);
				lazy[right(idx)] = Math.max(lazy[idx], lazy[right(idx)]);
			}
			lazy[idx] = 0;
		}
	}

	//Returns the index of the left child of a node
	private int left(int idx) {
		return 2*idx+1;
	}

	//Returns the index of the right child of a node
	private int right(int idx) {
		return 2*idx+2;
	}
}