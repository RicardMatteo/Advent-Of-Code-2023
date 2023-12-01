package Day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class day1 {
    
    public static void main(String[] args) {
        String path = "./Day1/input";
        try {
            String input = Files.readString(Paths.get(path));
            String[] inputArray = input.split("\n");
            int sum = 0;
            // loop through each line
            for (int i = 0; i < inputArray.length; i++) {
                Line line = new Line(inputArray[i]);
                line.replaceNum();
                sum += line.countLine();
            }
            System.err.println(sum);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
