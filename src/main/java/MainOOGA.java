import OOGA.GeneCatalog;
import ProblemData.EquationProblem;

import java.util.ArrayList;
import java.util.Arrays;

import static GA.LifeCycle.Evolve;
import static GA.RandomFunc.RandPopFrom;
import static GA.RandomFunc.RandRangeDouble;
import static GA.RandomFunc.RandRangeInt;
import static ProblemData.EquationProblem.INDIV_GENES_N;
import static ProblemData.EquationProblem.STEPS;

public class MainOOGA {

    public static void main(String[] args) {

        var catalog = new GeneCatalog()
                .addGenerator(() -> RandRangeInt(-5, 2)) // x
                .addGenerator(() -> RandRangeInt(-5, 2)) // y
                .addGenerator(() -> RandRangeInt(-5, 2)) // z
                .addGenerator(() -> RandRangeDouble(-10.0, 10.0)) // c
                .addGenerator(() -> RandRangeDouble(-10.0, 10.0)); // a

        var initialPop = RandPopFrom(catalog, INDIV_GENES_N, 4);

        var results = Evolve(initialPop, STEPS, 8, 0.5, EquationProblem::Fitness, catalog);

        results.forEach(res -> {
            System.out.printf(
                    "Solution: x=%d y=%d z=%d c=%f a=%f  ==> %f  %n",
                    res.get(0),
                    res.get(1),
                    res.get(2),
                    res.get(3),
                    res.get(4),
                    EquationProblem.Fitness(res)
            );
        });




    }
}
