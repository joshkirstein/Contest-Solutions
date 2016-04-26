/*
ID: joshkir1
LANG: JAVA
TASK: heritage
*/
import java.io.*;
import java.util.*;

class heritage
{
	static ArrayList<String> stck = new ArrayList<String>();
	static String preorder;
	static int idx;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("heritage.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));
		String inorder = sc.nextLine();
		preorder = sc.nextLine();
		idx = 0;
		run(inorder);
		for (String s : stck) {
			out.print(s);
		}
		out.println();
		out.close();
		System.exit(0);
	}

	public static void run(String inorder) {
		if (idx >= preorder.length()) return;
		char c = preorder.charAt(idx++);
		int in = -1;
		for (int i = 0; i < inorder.length(); i++) {
			if (inorder.charAt(i) == c) {
				in = i;
				break;
			}
		}
		if (in != -1) {
			if (in>=0)
				run(inorder.substring(0, in));
			if (in < inorder.length())
				run(inorder.substring(in+1, inorder.length()));
		} else {
			idx--;
			return;
		}
		stck.add(c + "");
	}
}