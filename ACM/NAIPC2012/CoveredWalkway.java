import java.util.*;

public class CoveredWalkway {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			long C = sc.nextLong();
			if (N == 0 && C == 0) break;
			long[] A = new long[N];
			for (int i = 0; i < N; i++) A[i] = sc.nextLong();
			long[] dp = new long[N];
			dp[0] = C;
			Lines ll = new Lines();
			if (N > 1) {
			ll.add(-2*A[1], dp[0]+A[1]*A[1]);
			for (int i = 1; i < N; i++) {
				dp[i] = A[i]*A[i]+C + ll.min(A[i]);
				dp[i] = Math.min(dp[i], (A[i]-A[0])*(A[i]-A[0])+C);
				if (i != N-1)
					ll.add(-2*A[i+1], dp[i]+A[i+1]*A[i+1]);
			}
			}
			System.out.println(dp[N-1]);
		}
	}
}
class Lines {
	Line first;
	Line cur;
	Line last;
	public void add(long slope, long inter) {
		if (cur == null) {
			cur = new Line(slope, inter);
			last = cur;
			first = cur;
			cur.breakAtX = Long.MAX_VALUE;
		} else {
			boolean passedCur = false;
			Line next = new Line(slope, inter);
			last.next = next;
			while (last.prev != null) {
				long breakAt = (long) ((last.inter-inter)/(slope-last.slope));
				if ((last.inter-inter)%(slope-last.slope) == 0) breakAt++;
				if (breakAt < last.prev.breakAtX) {
					if (last == cur) passedCur = true;
					last = last.prev;
				} else {
					break;
				}
			}
			if (last == null) {
				cur = next;
				last = next;
				next.breakAtX = Long.MAX_VALUE;
			} else {
				long breakAt = (long) ((last.inter-inter)/(slope-last.slope));
				last.next = next;
				last.breakAtX = breakAt;
				next.breakAtX = Long.MAX_VALUE;
				next.prev = last;
				last = next;
				if (passedCur) {
					cur = last;
					cur = next;
				}
			}
		}
	}
	public long min(long val) {
		while (val > cur.breakAtX) {
			cur = cur.next;
		}
		return cur.slope*val+cur.inter;
	}
}
class Line {
	long slope;
	long inter;
	static int idxCnt = 0;
	public Line(long slope, long inter) {
		this.slope = slope;
		this.inter = inter;
		this.idx = idxCnt++;
	}
	int idx;

	long breakAtX;


	Line next, prev;

}