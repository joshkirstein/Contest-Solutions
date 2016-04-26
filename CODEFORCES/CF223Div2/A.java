import java.util.*;

public class A {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int numCards = sc.nextInt();
	LinkedList<Integer> ll = new LinkedList<Integer>();
	for (int i = 0; i < numCards; i++) {
	    ll.add(sc.nextInt());
	}
	int p1Sum = 0, p2Sum = 0;
	boolean turn = true;//true = p1, false = p2
	while (!ll.isEmpty()) {
	    int num = 0;
	    if (ll.peekFirst() > ll.peekLast()) {
		num = ll.removeFirst();
	    } else {
		num = ll.removeLast();
	    }
	    if (turn) {
		p1Sum += num;
	    } else {
		p2Sum += num;
	    }
	    turn = !turn;
	}
	System.out.printf("%d %d\n", p1Sum, p2Sum);
    }
}
