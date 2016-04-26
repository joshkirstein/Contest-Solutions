package delos;

/*
ID: joshkir1
LANG: JAVA
TASK: example
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class example {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("example.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("example.out"));
        //input processing output here
        out.close();
    }
}