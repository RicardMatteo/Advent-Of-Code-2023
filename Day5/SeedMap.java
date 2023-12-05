package Day5;

public class SeedMap {
    
    double destRangeStart;
    double srcRangeStart;
    double rangeLen;
    double srcRangeEnd;

    public SeedMap(double drs, double srs, double rl) {
        destRangeStart = drs;
        srcRangeStart = srs;
        rangeLen = rl;
        srcRangeEnd = srs + rl - 1;
    }

    public double eval(double seed) {
        double diff = seed - srcRangeStart;
        if (diff >= 0 && diff <= rangeLen) {
            return (destRangeStart + diff);
        }
        return seed;
    }


    public boolean compare(SeedMap sm) {
        return this.srcRangeStart < sm.srcRangeStart;
    }
}
