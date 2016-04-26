
/*
ID: joshkir1
LANG: JAVA
TASK: zerosum
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class zerosum {
    
    //only N-1 places to put a +,-, ... 3<=N<=9...3^8 (Max) = 6561,
    //which is totally doable and we can evaluate the expression in O(n)
    //where n is the length of the whole expression (N + N - 1) = (2n - 1)
    //Time = O(3^(n - 1)*(2n-1))?
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("zerosum.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("zerosum.out"));
        //input processing output here
        int N = Integer.parseInt(sc.readLine());
        //1..2..3..4..5..6..7..N
        //1..2..3..4..5..6..N
        for (int i = 0; i < Math.pow(3, N - 1); i++) {
            String str = Integer.toString(i, 3);
            int length = str.length();
            length = (N - 1) - length - 1;
            StringBuilder br = new StringBuilder("");
            for (int j = 0; j <= length; j++) {
                br.append("0");
            }
            str = br.toString() + str;
            Stack<Integer> stk = new Stack<Integer>();
            for (int j = N; j >= 1; j--) {
                stk.push(j);
            }
            for (int j = 0; j < (N - 1); j++) {
                char c = str.charAt(j);
                if (c == '0') {
                    //combine together...
                    int top = stk.pop();
                    int top2 = stk.pop();
                    stk.push(Integer.parseInt("" + top + top2));
                    //System.out.println("COMBINING " + top + " AND " + top2 + " GOT: " + Integer.parseInt("" + top + top2));
                } else if (c == '1') {
                    int top = stk.pop();
                    for (int k = j + 1; k < (N - 1); k++) {
                        if (str.charAt(k) == '0') {
                            int poppy = stk.pop();
                            int doublePoppy = stk.pop();
                            stk.push(Integer.parseInt("" + poppy + doublePoppy));
                            j = k;
                        } else {
                            break;
                        }
                    }
                    int top2 = stk.pop();
                    stk.push(top + top2);//adding
                    //System.out.println("ADDING " + top + " AND " + top2);
                } else if (c == '2') {
                    int top = stk.pop();
                    for (int k = j + 1; k < (N - 1); k++) {
                        if (str.charAt(k) == '0') {
                            int poppy = stk.pop();
                            int doublePoppy = stk.pop();
                            stk.push(Integer.parseInt("" + poppy + doublePoppy));
                            j = k;
                        } else {
                            break;
                        }
                    }
                    int top2 = stk.pop();
                    stk.push(top - top2);//subtracting
                    //System.out.println("SUBBING " + top + " AND " + top2 + " " + j);
                }
            }
            if (stk.size() == 1) {
                if (stk.pop() == 0) {
                    //System.out.println(str);
                    for (int j = 1; j <= N; j++) {
                        out.append(j + "");
                        if (j != N) {
                            char c = str.charAt(j - 1);
                            if (c == '0') {
                                out.append(" ");
                            } else if (c == '1') {
                                out.append("+");
                            } else if (c == '2') {
                                out.append("-");
                            }
                        }
                    }
                    out.append("\n");
                }
            } else {
                System.out.println("ERROR WUT");
            }
        }
        out.close();
    }
}