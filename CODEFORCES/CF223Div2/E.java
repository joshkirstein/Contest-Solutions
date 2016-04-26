import java.util.*;
import java.io.*;

class Brackets {
    
    static int N;
    static int[] left;
    static int[] right;
    static int[] tree;
    public static void main(String[] args) throws Exception {
	BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
	String s = sc.readLine();
	/** build tree **/
	N = s.length();
	left = new int[N];
	right = new int[N];
	tree = new int[4*N];
	for (int i = 0; i < N; i++) {
	    if (s.charAt(i) == '(') {
		if (i != 0) {
		    right[i] += right[i-1];
		    left[i] += left[i-1];
		}
		left[i]++;
	    } else if (s.charAt(i) == ')') {
		if (i != 0) {
		    left[i] += left[i-1];
		    right[i] += right[i-1];
		}
		right[i]++;
	    }
	}
	build(0, 0, N-1);
	/** answer queries **/
	int queries = Integer.parseInt(sc.readLine());
	StringBuilder br = new StringBuilder("");
	for (int i = 0; i < queries; i++) {
	    String[] spl = sc.readLine().split(" ");
	    int l = Integer.parseInt(spl[0]);
	    int r = Integer.parseInt(spl[1]);
	    br.append(2*query(l-1,r-1)+"\n");
	}
	System.out.println(br.toString());
    }

    public static void build(int idx, int l, int r) {
	if (l == r) {
	    tree[idx] = 0;
	} else {
	    int left = L(idx);
	    int right = R(idx);
	    build(left, l, (l+r)/2);
	    build(right, (l+r)/2+1, r);
	    int max = tree[left] + tree[right];
	    //AND CROSSOVER:
	    //left is from [l, (l+r)/2]
	    //right is from [(l+r)/2 + 1, r]
	    int leftL = getLeft(l, (l+r)/2) - tree[left];
	    //int leftR = getRight(l, (l+r)/2) - tree[left];
	    //int rightL = getLeft((l+r)/2 + 1, r) - tree[right];
	    int rightR = getRight((l+r)/2+1, r) - tree[right];
	    max += Math.min(Math.max(0,leftL), Math.max(rightR,0));
	    tree[idx] = max;
	}
    }

    public static int getLeft(int l, int r) {
	int res = left[r];
	if (l > 0) res -= left[l-1];
	return res;
    }

    public static int getRight(int l, int r) {
	int res = right[r];
	if (l > 0) res -= right[l-1];
	return res;
    }

    public static int L(int N) {
        return 2 * N + 1;
    }

    public static int R(int N) {
        return 2 * N + 2;
    }

    public static int query(int i, int j) {
	return query(0, 0, N-1, i, j);
    }

    public static int query(int idx, int l, int r, int i, int j) {
	if (i > r || j < l) return 0;
	if (l >= i && r <= j) return tree[idx];
        int res = 0;//todo combine
	int leftQ = query(L(idx), l, (l+r)/2, i, j);
	int rightQ = query(R(idx), (l+r)/2+1, r, i, j);
	//i <= r and j >= l
	int maxL = Math.max(i, l);
	int minR = Math.min(j, r);
	//[maxL, (l+r)/2] U [(l+r)/2+1, minR]
	int left = getLeft(maxL, (l+r)/2)-leftQ;
	int right = getRight((l+r)/2+1, minR)-rightQ;
	return leftQ+rightQ+Math.min(Math.max(0, left), Math.max(0, right));
    }
}
