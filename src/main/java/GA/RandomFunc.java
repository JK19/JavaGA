package GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomFunc {

    public static double RandDouble() {
        return Math.random();
    }

    // [min, max]
    public static int RandRangeInt(int min, int max) {
        return (int) ( Math.random() * ((max-min) + 1) ) + min;
    }

    public static double RandRangeDouble(double min, double max) {
        return ( Math.random() * ((max-min) + 1) ) + min;
    }

    public static <T> T RandFrom(List<T> collection) {
        return collection.get( RandRangeInt(0, collection.size()-1) );
    }


    public static <T> List<T> RandIndivFrom(List<T> geneList, int nGenes) {
        return Stream.generate(() -> RandFrom(geneList))
                .limit(nGenes)
                .collect(Collectors.toList());
    }

    public static <T> List<List<T>> RandPopFrom(List<T> geneList, int nGenes, int size) {
        return Stream.generate(()-> RandIndivFrom(geneList, nGenes))
                .limit(size)
                .collect(Collectors.toList());
    }


}
