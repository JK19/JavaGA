package GA;

import OOGA.GeneCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        try {
            return collection.get(RandRangeInt(0, collection.size() - 1));
        } catch (IndexOutOfBoundsException ex){
            return null;
        }
    }


    public static List<Object> RandIndivFrom(GeneCatalog catalog, int nGenes) {
        var ret = new ArrayList<Object>();
        // IntStream.range(0, nGenes).forEach((i) -> ret.add( RandFrom(catalog.getFor(i)) ));
        IntStream.range(0, nGenes).forEach( i -> ret.add(catalog.randForSlot(i)));
        return ret;
    }

    public static List<List<Object>> RandPopFrom(GeneCatalog catalog, int nGenes, int size) {
        return Stream.generate(()-> RandIndivFrom(catalog, nGenes))
                .limit(size)
                .collect(Collectors.toList());
    }


}
