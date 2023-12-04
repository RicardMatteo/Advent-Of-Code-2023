package Day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day2 {

    static ArrayList<Integer> game_ok = new ArrayList<Integer>();
    //12 red cubes, 13 green cubes, and 14 blue cubes
    public static void main(String[] args) {
        
        String path = "./Day2/input";
        try {
            String input = Files.readString(Paths.get(path));
            String[] inputArray = input.split("\n");
            int res1 = 0;
            int res2 = 0;

            for (int i = 0; i < inputArray.length; i++) {
                String line = inputArray[i];
                
                // Part 1
                if (checkLine(line)) {
                    System.out.println("Game :" + i+1 + " is ok");
                    game_ok.add(i+1);
                    res1 += (i+1);
                }
                // Part 2
                res2 += powerLine(line);
            }

            System.out.println("Part 1 : " + res1);
            System.out.println("Part 2 : " + res2);

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public static boolean checkLine(String line) {
        String[] sep_game = line.split(":");
        String[] sets = sep_game[1].split(";");
        for (int j = 0; j < sets.length; j++) {
            sets[j] = sets[j].trim();
            String[] words = sets[j].split(",");
            
                
            for (int i = 0; i < words.length; i++) {
                int red_dispo = 12;
                int green_dispo = 13;
                int blue_dispo = 14;
                
                int nb = Integer.parseInt(words[i].trim().split(" ")[0]);
                String couleur = words[i].trim().substring(2).trim();
                if (couleur.equals("red"))
                    {
                    red_dispo -= nb;
                    if(red_dispo < 0) {
                        return false;
                    }
                }
                else if (couleur.equals("blue"))
                {
                    blue_dispo -= nb;
                    if(blue_dispo < 0) {
                        return false;
                    }
                }
                else if (couleur.equals("green"))
                {
                    green_dispo -= nb;
                    if(green_dispo < 0) {
                        return false;
                    }
                }
                else {
                    System.out.println("mauvaise couleur" + couleur);
                    return false;
                }
            }
        
        
        }
        return true;
    }

    public static int powerLine(String line) {
        String[] sep_game = line.split(":");
        String[] sets = sep_game[1].split(";");
        
        int red_min = 0;
        int green_min = 0;
        int blue_min = 0;
        for (int j = 0; j < sets.length; j++) {
            sets[j] = sets[j].trim();
            System.out.println(sets[j]);
            String[] words = sets[j].split(",");


            for (int i = 0; i < words.length; i++) {
                int red_sum = 0;
                int green_sum = 0;
                int blue_sum = 0;
                int nb = Integer.parseInt(words[i].trim().split(" ")[0]);
                String couleur = words[i].trim().substring(2).trim();
                if (couleur.equals("red"))
                {
                    red_sum += nb;
                    if(red_min < red_sum) {
                        red_min = red_sum;
                    }
                }
                else if (couleur.equals("blue"))
                {
                    blue_sum += nb;
                    if(blue_min < blue_sum) {
                        blue_min = blue_sum;
                    }
                }
                else if (couleur.equals("green"))
                {
                    green_sum += nb;
                    if(green_min < green_sum) {
                        green_min = green_sum;
                    }
                }
                else {
                    System.out.println("mauvaise couleur" + couleur);
                    return 0;
                }
            }
        
        
        }
        System.out.println("red_min : " + red_min + " green_min : " + green_min + " blue_min : " + blue_min);
        // return the minimum of the three colors multiplied together
        return (red_min * green_min * blue_min);
    }

}
