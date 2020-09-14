import java.io.File;            // required libraries
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {         // main class for testing.
    public static void main(String[] args) {
        Scanner scan;
        Graph graph = new Graph();
        try {
            scan = new Scanner( new File("input.txt")); // input file. ( at intellij project file )
            graph.createGraph(scan);        // createGraph using input file.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
