package Day1;

import java.util.ArrayList;
import java.util.Arrays;

public class Line {
    private char firstNum;
    private char lastNum;
    private String line;
    private ArrayList<String> numberStrings = new ArrayList<String>(
            Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
    private ArrayList<String> numberStringsReplace = new ArrayList<String>(
            Arrays.asList("0o", "o1e", "t2o", "t3e", "f4r", "f5e", "s6x", "s7n", "e8t", "n9e"));

    public Line(String line) {
        this.line = line;
    }

    public void replaceNum() {
        for (int i = 0; i < numberStrings.size(); i++) {
            if (line.contains(numberStrings.get(i))) {
                line = line.replace(numberStrings.get(i), numberStringsReplace.get(i));
            }
        }
    }

    public int countLine() {
        firstNum = 'n';
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                if (firstNum == 'n') {
                    firstNum = line.charAt(i);
                }
                lastNum = line.charAt(i);
            }
        }
        return Integer.parseInt(firstNum + "" + lastNum);
    }
}