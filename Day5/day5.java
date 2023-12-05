package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class day5 {
    

    public static void main(String[] args) throws IOException {
        String path = "Day5/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");
        String[] l1 = lines[0].split(" ");
        
        ArrayList<Double> seeds1 = new ArrayList<Double>();
        ArrayList<Double> seeds2 = new ArrayList<Double>();
        
        for (int i = 1; i < l1.length; i++) {
            seeds1.add(Double.parseDouble(l1[i]));
            seeds2.add(Double.parseDouble(l1[i]));
        }


        int i = 3;
        while (i < lines.length) {
            ArrayList<SeedMap> seedMaps = new ArrayList<SeedMap>();

            ArrayList<Double> nextSeeds1 = new ArrayList<Double>();
            ArrayList<Double> nextSeeds2 = new ArrayList<Double>();

            while (i < lines.length && !lines[i].isEmpty()) {
                SeedMap sm = new SeedMap(Double.parseDouble(lines[i].split(" ")[0]), Double.parseDouble(lines[i].split(" ")[1]), Double.parseDouble(lines[i].split(" ")[2]));
                seedMaps.add(sm);
                i++;
            }
            seedMaps.sort((a, b) -> a.compare(b) ? -1 : 1);
            
            // Step 1            
            step1(seedMaps, seeds1, nextSeeds1);
            seeds1 = nextSeeds1;
            
            // Step 2
            step2(seedMaps, seeds2, nextSeeds2);
            seeds2 = nextSeeds2;
            
            i+= 2;
        }
        
        System.out.println("Part 1 : " + min(seeds1));
        System.out.println("Part 2 : " + min2(seeds2));
        


    }

    public static ArrayList<SeedMap> getSeedMaps(String[] lines, int index) {
        ArrayList<SeedMap> seedMaps = new ArrayList<SeedMap>();
        int i = index;
        while (!lines[i].isEmpty()) {
            SeedMap sm = new SeedMap(Double.parseDouble(lines[i].split(" ")[0]), Double.parseDouble(lines[i].split(" ")[1]), Double.parseDouble(lines[i].split(" ")[2]));
            seedMaps.add(sm);
            i++;
        }
        return seedMaps;
    }

    public static void step1(ArrayList<SeedMap> seedMaps, ArrayList<Double> seeds, ArrayList<Double> res) {
        for (Double seed : seeds) {
                res.add(eval(seed, seedMaps));
            }
    }

    public static void step2(ArrayList<SeedMap> seedMaps, ArrayList<Double> seeds, ArrayList<Double> res) {
        for (int j = 0; j < seeds.size(); j+=2) {
            double seed = seeds.get(j);
            double range = seeds.get(j+1);
            eval2(seed, range, seedMaps, res);
        }
    }


    public static void eval2 (double seed, double range, ArrayList<SeedMap> seedMaps, ArrayList<Double> nextSeeds) {
        double seedEnd = seed + range - 1;
        for(SeedMap sm : seedMaps) {
            // Le début de la seed est plus petit que celui du mapping
            if (seed < sm.srcRangeStart) {
                // La fin de la seed est plus petit que le début du mapping -> on a fini de mapper
                if (seedEnd < sm.srcRangeStart) {
                    ajoutSeed(nextSeeds, seed, range);
                    return;
                }
                // Sinon,
                // on divise la seed en deux seed : seed, sm.start - 1] et [sm.start, seedEnd],
                // on ajoute la seed correspondant à [seed, sm.start - 1] pour la prochaine passe,
                ajoutSeed(nextSeeds, seed, sm.srcRangeStart - 1);
                // on va maintenant traiter [sm.start, seedEnd]
                seed = sm.srcRangeStart;
            }
            // Si le début de la seed est plus grand que la fin de ce mapping
            if (seed > sm.srcRangeEnd) {
                // alors on passe au prochain mapping
                continue;
            }
            // Si la fin de la seed est inclue dans ce mapping
            if (seedEnd <= sm.srcRangeEnd) {
                // on ajoute la seed
                double nextSeed = sm.eval(seed);
                double nextRange = sm.eval(seedEnd) - nextSeed;
                ajoutSeed(nextSeeds, nextSeed, nextRange);
                // on a fini pour cette seed
                return;
            }
            // Sinon
            // ce mapping peut traiter [seed, sm.end]
            double nextSeed = sm.eval(seed);
            double nextRange = sm.eval(sm.srcRangeEnd) - nextSeed;
            ajoutSeed(nextSeeds, nextSeed, nextRange);
            // et il restera [sm.end + 1, seedEnd] à faire traiter par les mapping suivant
            seed = sm.srcRangeEnd + 1;
        }
        // Si aucun mapping ne correspond :
        ajoutSeed(nextSeeds, seed, range);
    }

    public static void ajoutSeed(ArrayList<Double> array, double seed, double range) {
                    array.add(seed);
                    array.add(range);
    }


    public static double eval(double seed, ArrayList<SeedMap> seedMaps) {
        double res = seed;
        for (SeedMap sm : seedMaps) {
            res = sm.eval(seed);
            if (res != seed) {
                return res;
            }
        }
        return res;
    }

    public static double min(ArrayList<Double> seeds) {
        double min = seeds.get(0);
        for (Double seed : seeds) {
            if (seed < min) {
                min = seed;
            }
        }
        return min;
    }

    public static double min2(ArrayList<Double> seeds) {
        double min = seeds.get(0);
        for (int j = 0; j < seeds.size(); j+=2) {
            if (seeds.get(j) < min) {
                min = seeds.get(j);
            }
        }
        return min;
    }   
}
