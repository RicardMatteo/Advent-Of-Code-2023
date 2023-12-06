package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day6 {
    
    public static void main(String[] args) throws IOException {
        String path = "Day6/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");

        // part 1
        String[] distances = lines[1].split("\\s+");
        String[] times = lines[0].trim().split("\\s+");
        
        int res1 = 1;
          
        for (int i = 1; i < times.length; i++) { 
            res1 *= calcNumberOfWay(Integer.parseInt(times[i]), Integer.parseInt(distances[i]));
        }
        
        
        // part 2
        double time2 = Double.parseDouble(lines[0].split(":")[1].replaceAll("\\s",""));
        double distance2 = Double.parseDouble(lines[1].split(":")[1].replaceAll("\\s", ""));
        
        int res2 = calcNumberOfWay(time2, distance2);

        System.out.println("Part 1 : " + res1);
        System.out.println("Part 2 : " + res2);

    }

    public static int calcNumberOfWay(int time, int distance) {
        int count = 0;
        int timeHold = 0;
        while (calcDist(timeHold, time) < distance && timeHold < time) {
            timeHold++;
        }

        while (calcDist(timeHold, time) >= distance && timeHold < time) {
            count++;
            timeHold++;
        }

        return count;
    }

    public static int calcNumberOfWay(double time, double distance) {
        int count = 0;
        double timeHold = 0;
        while (calcDist(timeHold, time) < distance && timeHold < time) {
            timeHold++;
        }

        while (calcDist(timeHold, time) >= distance && timeHold < time) {
            count++;
            timeHold++;
        }

        return count;
    }

    public static int calcDist(int timeHold, int time) {
        return timeHold * (time - timeHold) ;
    }

    public static double calcDist(double timeHold, double time) {
        return timeHold * (time - timeHold) ;
    }
}
