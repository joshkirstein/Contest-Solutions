import java.util.*;

public class B {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder number = new StringBuilder(sc.next());
		int len = number.length();
		int swapDig = -1;
		int swapidx = -1;
		int firstDig = (int)(number.charAt(len-1)-'0');
		for (int i = len-1; i >= 0; i--) {
			int dig = (int)(number.charAt(i)-'0');
			if (dig % 2 == 0) {
				if (swapDig == -1 || firstDig >= dig) {
					swapDig = dig;
					swapidx = i;
				}
			}
		}
		if (swapidx == -1) {
			System.out.println("-1");
			return;
		}
		if (swapidx == 0) {
			if (swapDig == 0) {
				System.out.println("-1");
				return;
			} else {
				number.setCharAt(swapidx, (char)(firstDig+'0'));
				number.setCharAt(len-1, (char)(swapDig+'0'));
			}
		} else {
		
				number.setCharAt(swapidx, (char)(firstDig+'0'));
				number.setCharAt(len-1, (char)(swapDig+'0'));
		}
		
		System.out.println(number);
	}
}
