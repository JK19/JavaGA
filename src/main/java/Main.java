import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


import static GA.RandomFunc.*;
import static GA.LifeCycle.*;
import static ProblemData.ContainerProblem.MAX_WEIGHT;
import static ProblemData.ContainerProblem.MAX_CONTAINERS;
import static ProblemData.ContainerProblem.STEPS;
import static ProblemData.ContainerProblem.totalPrice;
import static ProblemData.ContainerProblem.totalWeight;

import GA.Runner;
import OOGA.GeneCatalog;
import ProblemData.Container;
import ProblemData.ContainerProblem;

public class Main {

    public static void main(String[] args) {

        var GeneList = new ArrayList<Container>();
        GeneList.add(new Container("A", 3, 2));
        GeneList.add(new Container("B", 10, 7));
        GeneList.add(new Container("C", 23, 11));
        GeneList.add(new Container("Z", 0, 0));

        var catalog = new GeneCatalog()
                .addSourceWithSpan(MAX_CONTAINERS, GeneList);


        var initialPop = RandPopFrom(catalog, MAX_CONTAINERS, 6);

        var results = Evolve(initialPop, STEPS, 8, 0.4, ContainerProblem::Fitness, catalog);

        results.forEach(res -> {
            System.out.printf(
                    "Solution: %s totalw:%.2f , totalp:%.2f , fitness:%.2f %n",
                    res.toString(),
                    totalWeight(res),
                    totalPrice(res),
                    ContainerProblem.Fitness(res)
            );
        });


    }
}
