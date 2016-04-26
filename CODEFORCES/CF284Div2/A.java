import java.util.*;

public class A {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), x = sc.nextInt();
		int[] start = new int[n];
		int[] end = new int[n];
		for (int i = 0; i < n; i++) {
			start[i] = sc.nextInt();
			end[i] = sc.nextInt();
		}
		int curPart = 0;
		int cur = 1;
		int watch = 0;
		while (curPart < n) {
			int st = start[curPart];
			int en = end[curPart];
			if (cur == st) {
				//watch everything
				int amt = en-st+1;
				watch+=amt;
				cur=en+1;
				curPart++;
			} else {
				if ((cur+x) <= st) {
					cur += x;
				} else {
					cur += 1;
					watch++;
				}
			}
		}
		System.out.println(watch);
	}
}