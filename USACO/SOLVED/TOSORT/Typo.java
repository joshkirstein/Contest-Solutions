import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class Typo {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("typo.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("typo.out"));
        String expression = sc.readLine();
        char[] exp = expression.toCharArray();
        int amt = 0;
        if (exp.length % 2 == 0) {
            int[] PF = new int[exp.length];
            int[] PB = new int[exp.length];
            for (int i = 0; i < exp.length; i++) {
                if (exp[PB.length - i - 1] == '(') {
                    if (i == 0) {
                        PB[PB.length - i - 1] = 1;
                    } else {
                        PB[PB.length - i - 1] = PB[PB.length - i] + 1;
                    }
                } else if (exp[PB.length - i - 1] == ')') {
                    if (i == 0) {
                        PB[PB.length - i - 1] = -1;
                    } else {
                        PB[PB.length - i - 1] = PB[PB.length - i] - 1;
                    }
                }
                if (exp[i] == '(') {
                    if (i == 0) {
                        PF[i] = 1;
                    } else {
                        PF[i] = PF[i - 1] + 1;
                    }
//                    exp[i] = ')';
//                    if (isBalanced(exp)) {
//                        System.out.println(i);
//                        amt++;
//                    }
//                    exp[i] = '(';
                } else if (exp[i] == ')') {
                    if (i == 0) {
                        PF[i] = -1;
                    } else {
                        PF[i] = PF[i - 1] - 1;
                    }
//                    exp[i] = '(';
//                    if (isBalanced(exp)) {
//                        System.out.println(i);
//                        amt++;
//                    }
//                    exp[i] = ')';
                }
            }
            for (int i = 0; i < exp.length; i++) {
                if (exp[i] == '(') {
                    
                } else if (exp[i] == ')') {
                    
                }
            }
        }
        out.append(amt + "\n");
        out.close();
    }
    
    public static boolean isBalanced(char[] exp) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == '('){
                stack.push('(');
            } else if (exp[i] == ')') {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }
}
