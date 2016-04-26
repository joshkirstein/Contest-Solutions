import java.util.*;

public class B {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int numCards = sc.nextInt();
	//notice how the range of numbers is: [1, 5000]
	int[] count = new int[5001];
	for (int i = 0; i < numCards; i++) {
	    count[sc.nextInt()]++;
	}
	String output = "";
	int last = -1;
	int sum = 0;
	for (int i = 1; i < count.length; i++) {
	    if (count[i] > 0) {
            sum++;
            count[i]--;
            output += (i + " ");
            last = i;
	    }
	}
	for (int i = last - 1; i >= 1; i--) {
	    if (count[i] > 0) {
            sum++;
            count[i]--;
            output += (i + " ");
	    }
	}
	System.out.printf("%d\n%s\n", sum, output);//Output okay with extra space
    }
}
