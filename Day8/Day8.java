package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day8 {

 

    public static void main(String[] args) throws IOException {
        
        // Reading the file 
        String path = "Day8/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");
        String[] dir = lines[0].split("");

        // init values
        HashMap<String,Tuple<String>> map = new HashMap<>();
        List<Tuple<String>> currentsString = new ArrayList<Tuple<String>>();
        int remaining = 0;

        // Creating the map
        for (int i = 2; i<lines.length; i++) {
            // parsing the line
            String[] line = lines[i].split("=");
            String src = line[0].replace("(", "").trim();
            Tuple<String> dests = new Tuple<String>(line[1].replace("(", "").replace(")", "").trim().split(", "));
             
            map.put(src, dests);

            // Checking if start pos
            if(src.charAt(2) == 'A') {
                currentsString.add(new Tuple<String>(src,src));
                remaining++;
            }

        }

        

        // calcul of the steps
        int stepCount = 0;
        
        // count needs to be a long
        long count = 1;

        // Part 2
        while (remaining > 0) {
            // For each node
            for (int i = 0; i < currentsString.size(); i++) {

                // Find next node
                Tuple<String> currentString = currentsString.get(i);
                if(dir[stepCount % dir.length].equalsIgnoreCase("L")) {
                    currentString.right = (map.get(currentString.right)).left;
                } else {
                    currentString.right = (map.get(currentString.right)).right;
                } 

                // check if end with Z and similar to the corresponding start
                if (currentString.right.charAt(2) == 'Z') {
                    remaining--;
                    i--;
                    System.out.println(count + " " + stepCount);
                    count = lcm(stepCount + 1, count);
                    currentsString.remove(currentString);
                }
            
            }

            stepCount++;
        }

        // Print the result
        System.out.println(count);

    }

    // lcm of 2 long
    private static long lcm(long a,long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        a = Math.abs(a);
        b = Math.abs(b);
        long high = Math.max(a, b);
        long low = Math.min(a, b);
        long lcm = high;
        while (lcm % low != 0) {
            lcm += high;
        }
        return lcm;
    }
}