package ProblemData;

import java.util.List;

public class EquationProblem {

    public static final int STEPS = 1_000_000;
    public static final int INDIV_GENES_N = 5;

    public static Double Fitness(List<Object> indiv) {

        var x = (int) indiv.get(0);
        var y = (int) indiv.get(1);
        var z = (int) indiv.get(2);
        var c = (Double) indiv.get(3);
        var a = (Double) indiv.get(4);

        var eval = 3 * Math.pow(x, 3) + 2 * Math.pow(y, 2) + z - Math.pow(c, 3) - Math.pow(a, 2);

        var res = Math.abs( 300.234 - eval);

        return res;
    }
}
