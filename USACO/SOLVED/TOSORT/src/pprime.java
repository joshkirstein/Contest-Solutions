/*
ID: joshkir1
LANG: JAVA
TASK: pprime
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class pprime {
    
    static BufferedWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("pprime.in"));
        out = new BufferedWriter(new FileWriter("pprime.out"));
        String[] spl = sc.readLine().split(" ");
        int a = Integer.parseInt(spl[0]);
        int b = Integer.parseInt(spl[1]);
        if (genSing(a, b)) {
            return;
        }
        if (genDoub(a, b)) {
            out.close();
            return;
        }
        if (genTrip(a, b)) {
            out.close();
            return;
        }
        if (genQuad(a, b)) {
            out.close();
            return;
        }
        if (genPent(a, b)) {
            out.close();
            return;
        }
        if (genHept(a, b)) {
            out.close();
            return;
        }
        if (genSept(a, b)) {
            out.close();
            return;
        }
        if (genOct(a, b)) {
            out.close();
            return;
        }
        out.close();
    }
    
    public static boolean isPrime(int num) {
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num) + 1; i += 2) {//only need to check up to sqrt(num) ;)
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean genSing(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            if (i > b) {
                return true;
            }
            if (i >= a) {
                if (isPrime(i)) {
                    out.append(i + "\n");
                }
            }
        }
        return false;
    }
    
    public static boolean genDoub(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            int num = 10*i + i;
            if (num > b) {
                return true;
            }
            if (num >= a) {
                if (isPrime(num)) {
                    out.append(num + "\n");
                }
            }
        }
        return false;
    }
    
    public static boolean genTrip(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                //i is outside, j is middle
                int num = 100*i + 10*j + i;
                if (num > b) {
                    return true;
                }
                if (num >= a) {
                    if (isPrime(num)) {
                        out.append(num + "\n");
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean genQuad(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                int num = 1000*i + 100*j + 10*j + i;
                if (num > b) {
                    return true;
                }
                if (num >= a) {
                    if (isPrime(num)) {
                        out.append(num + "\n");
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean genPent(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    int num = 10000*i + 1000*j + 100*k + 10*j + i;
                    if (num > b) {
                        return true;
                    }
                    if (num >= a) {
                        if (isPrime(num)) {
                            out.append(num + "\n");
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean genHept(int a, int b) throws IOException {
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    int num = 100000*i + 10000*j + 1000*k + 100*k + 10*j + i;
                    if (num > b) {
                        return true;
                    }
                    if (num >= a) {
                        if (isPrime(num)) {
                            out.append(num + "\n");
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean genSept(int a, int b) throws IOException {
        //ijklkji
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    for (int l = 0; l <= 9; l++) {
                        int num = 1000000*i + 100000*j + 10000*k + 1000*l + 100*k + 10*j + i;
                        if (num > b) {
                            return true;
                        }
                        if (num >= a) {
                            if (isPrime(num)) {
                                out.append(num + "\n");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean genOct(int a, int b) throws IOException {
        //ijkllkji
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    for (int l = 0; l <= 9; l++) {
                        int num = 10000000*i + 1000000*j + 100000*k + 10000*l + 1000*l + 100*k + 10*j + i;
                        if (num > b) {
                            return true;
                        }
                        if (num >= a) {
                            if (isPrime(num)) {
                                out.append(num + "\n");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}