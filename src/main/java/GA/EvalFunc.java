package GA;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class EvalFunc {

    public static <T> int CompareFitness(T a, T b, Function<T, Double> fitnessFunc) {

        Double fa = fitnessFunc.apply(a);
        Double fb = fitnessFunc.apply(b);

        if(fa > fb){ return 1; }
        if(fa < fb){ return -1; }

        return 0; // fa == fb
    }

    public static <T> Comparator<T> BuildComparatorFor(BiFunction<T, T, Integer> func) {
        return (o1, o2) -> func.apply(o1, o2);
    }
}
