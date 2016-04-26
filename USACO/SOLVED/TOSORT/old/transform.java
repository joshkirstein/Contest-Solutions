package old;

/*
ID: joshkir1
LANG: JAVA
TASK: transform
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class transform {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("transform.in")));
        PrintWriter out = new PrintWriter(new FileWriter("transform.out"));
        int N = Integer.parseInt(sc.nextLine());
        char[][] before = new char[N][N];
        char[][] after = new char[N][N];
        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < N; j++) {
                before[i][j] = line.charAt(j);
            }
        }
        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < N; j++) {
                after[i][j] = line.charAt(j);
            }
        }
        if (equals(rot90(before), after)) {
            out.println("1");
        } else if (equals(rot180(before), after)) {
            out.println("2");
        } else if (equals(rot270(before), after)) {
            out.println("3");
        } else if (equals(reflect(before), after)) {
            out.println("4");
        } else if (equals(reflect(rot90(before)), after)) {
            out.println("5");
        } else if (equals(reflect(rot180(before)), after)) {
            out.println("5");
        } else if (equals(reflect(rot270(before)), after)) {
            out.println("5");
        } else if (equals(before, after)) {
            out.println("6");
        } else {
            out.println("7");
        }
        out.close();
        System.exit(0);
    }
    
    public static boolean equals(char[][] array, char[][] other) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i][j] != other[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*public static int cs(char[][] current, char[][] goal, ArrayList<Integer> visited, int cur, int curMin) {
        if (visited.contains(Arrays.hashCode(current))) {
            return 1000000;
        }
        if ((cur) >= curMin || (cur + 1) >= curMin) return 1000000;
        visited.add(Arrays.hashCode(current));
        if (equals(current, goal)) {            
            return cur;
        } else {
            int min = cs(rot90(current), goal, visited, cur + 1, Integer.MAX_VALUE);
            min = Math.min(cs(rot180(current), goal, visited, cur + 1, min), min);
            min = Math.min(cs(rot270(current), goal, visited, cur + 1, min), min);
            min = Math.min(cs(reflect(current), goal, visited, cur + 1, min), min);
            min = Math.min(cs(rot90(reflect(current)), goal, visited, cur + 1, min), min);
            min = Math.min(cs(rot180(reflect(current)), goal, visited, cur + 1, min), min);
            min = Math.min(cs(rot270(reflect(current)), goal, visited, cur + 1, min), min);
            return min;
        }
    }*/
    
    public static char[][] rot90(char[][] array) {
        char[][] newA = new char[array.length][array.length];
        int row = 0;
        int col = 0;
        for (int i = 0; i < array.length; i++) {
            col = 0;
            for (int j = array.length - 1; j >= 0; j--) {
                newA[row][col] = array[j][i];
                col++;
            }
            row++;
        }
        return newA;
    }
    
    public static char[][] rot180(char[][] array) {
        char[][] newA = rot90(array);
        newA = rot90(newA);
        return newA;
    }
    
    public static char[][] rot270(char[][] array) {
        char[][] newA = rot90(array);
        newA = rot90(newA);
        newA = rot90(newA);
        return newA;
    }
    
    public static char[][] reflect(char[][] array) {
        char[][] newA = new char[array.length][array.length];
        int curCol = 0;
        for (int i = array.length - 1; i >= 0; i--, curCol++) {
            for (int j = 0; j < array.length; j++) {
                newA[j][curCol] = array[j][i];
            }
        }
        return newA;
    }
}