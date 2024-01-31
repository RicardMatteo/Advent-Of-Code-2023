package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws IOException {

        // Reading the file 
        String path = "Day9/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");
        int part1 = 0;
        int part2 = 0;
        for (String line : lines) {
            part1 += calcLine(line, false);
            part2 += calcLine(line, true);
        }

        // Print the result 
        System.out.println("Part 1 : " + part1);
        
    }

    

    public static int calcLine(String line, Boolean isPart2) {
        // init values
        int res = 0;
        List<Integer> currentLine = new ArrayList<Integer>();
        List<Integer> nextLine = new ArrayList<Integer>();
        List<List<Integer>> pastLines = new ArrayList<List<Integer>>();
        Boolean found = false;

        // Get the numbers of the line
        for (String nm : line.split(" ")) {
            currentLine.add(Integer.parseInt(nm));
        }

        // For the part 2 we just need to reverse the list
        currentLine = currentLine.reversed();

        // Building the numbers
        while (!found) {
            
            int diff;
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

        // Finding the missing values
        for (int i = pastLines.size()-2; i>=0; i--){
            res = pastLines.get(i+1).getLast() + pastLines.get(i).getLast();
            pastLines.get(i)
                .add(res);
            
        }

        // System.out.println(res);

        return res;
    }

        
}
