package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Day8.Tuple;

/**
 * Day9
 */
public class Day9 {

    /**
     * Main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Reading the file 
        String path = "Day9/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");
        Tuple<Integer> res = new Tuple<Integer>(0,0);
        Tuple<Integer> temp = new Tuple<Integer>(0,0);
        
        for (String line : lines) {
            temp = calcLine(line);
            res.left += temp.left;
            res.right += temp.right;

        }

        // Print the result 
        System.out.println("Part 1 : " + res.left);
        System.out.println("Part 2 : " + res.right);
        
    }

    /**
     * Build the numbers
     * @param currentLine the current line
     * @param nextLine the next line
     * @param pastLines the past lines
     */
    public static void buildNumers(List<Integer> currentLine, List<Integer> nextLine, List<List<Integer>> pastLines) {
        int diff;
        Boolean found = false;
        while (!found) {
            
            found = true;
            
            // calc the diff in the current line
            for (int i = 1; i<currentLine.size(); i++) {
                diff = (currentLine.get(i) - currentLine.get(i-1));
                if (diff != 0) {
                    found = false;
                }
                nextLine.add(diff);
            }

            // Copy the arrays
            pastLines.add(new ArrayList<>(currentLine));
            currentLine = new ArrayList<>(nextLine);
            nextLine.clear();

            // print for debug
            // System.out.println(currentLine);
        }

        // Add 0 to the last line
        currentLine.add(0);
        pastLines.add(currentLine);
    }
    

    /**
     * Find the missing values
     * @param pastlines the past lines
     * @return the top missing value
     */
    public static int findValues(List<List<Integer>> pastlines) {
        int res = 0;
        for (int i = pastlines.size()-2; i>=0; i--){
            res = pastlines.get(i+1).getLast() + pastlines.get(i).getLast();
            pastlines.get(i)
                .add(res);
            
        }
        return res;
    }

    /**
     * Calculate the line
     * @param line the line to calculate
     * @return the tuple of the result
     */
    public static Tuple<Integer> calcLine(String line) {
        // init values
        List<Integer> currentLine = new ArrayList<Integer>();
        List<Integer> nextLine = new ArrayList<Integer>();
        
        // Get the numbers of the line
        for (String nm : line.split(" ")) {
            currentLine.add(Integer.parseInt(nm));
        }
        
        // For the part 2 we just need to reverse the list        
        Tuple<List<Integer>> lines = new Tuple<List<Integer>>(currentLine, new ArrayList<Integer>(currentLine).reversed());
        Tuple<List<List<Integer>>> pastLines = new Tuple<List<List<Integer>>>(new ArrayList<List<Integer>>(), new ArrayList<List<Integer>>());

        // Building the numbers
        buildNumers(lines.left, nextLine, pastLines.left);
        buildNumers(lines.right, nextLine, pastLines.right);

        // Finding the missing values
        return new Tuple<Integer> (findValues(pastLines.left), findValues(pastLines.right));

    }

        
}
