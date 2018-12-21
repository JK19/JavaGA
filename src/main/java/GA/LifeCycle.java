package GA;


import OOGA.GeneCatalog;

import static GA.EvalFunc.*;
import static GA.GeneManipulation.*;
import static GA.RandomFunc.*;
import static GA.Runner.*;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LifeCycle {





    // Executes one single cycle for population
    // Doesn't modifiy the lists passed as arguments
    // population : initial or previous population of individuals
    // alphabet : list of available genes for any given individual's ADN
    // pmut : probability of mutation for an individual in a population
    // fitnessF : fitness function evaluating an individual
    public static Tuple<List<List<Object>>, List<Object>> PopulationStep(
            List<List<Object>> population,
            GeneCatalog catalog,
            double pmut,
            Function<List<Object>, Double> fitnessF
    ){

        // local copies
        population = new ArrayList<List<Object>>(population);


        int adnlen = population.get(0).size(); // adn length
        int poplen = population.size();

        // sort population
        Collections.sort(population, (a, b) -> CompareFitness(a, b, fitnessF));
        Collections.reverse(population); // reverse order, best fitness first

        // get most performant indivs
        var alpha = population.get(0);
        var beta = population.get(1);
        var best = alpha;

        // mate best performant
        var res = Crossover(alpha, beta);

        // unpack res Tuple
        alpha = res.value1;
        beta = res.value2;

        // rebuild population
        population.clear();
        population.add(alpha);
        population.add(beta);
        population.addAll( RandPopFrom(catalog, adnlen, poplen-2) );

        // mutate population
        population = population.stream()
                .map( indiv -> Mutate(indiv, pmut, catalog) )
                .collect(Collectors.toList());

        return new Tuple(population, best);
    }




    public static List<Object> RunGenerations(
            List<List<Object>> population,
            int steps,
            GeneCatalog catalog,
            Double pmut,
            Function<List<Object>, Double> fitness
    ) {

        // local copy
        population = new ArrayList<>(population);

        // most performant indivs
        List<Object> top = null;
        List<Object> best = null;

        //var log = Logger.getLogger("RunGenerations");

        for (int step = 0; step < steps ; step++) {

            var res = PopulationStep(population, catalog, pmut, fitness);

            // log generation
            //if(step % 1_000 == 0) log.info(String.format("running step %d", step));

            // unpack res Tuple
            population = res.value1;
            best = res.value2;

            if (top == null) top = res.value2;

            if (fitness.apply(best) > fitness.apply(top)) {
                top = best;
            }
        }

        return top;
    }

    // Launches evolution environments in multiple cores
    // returns : list of solutions
    public static List<List<Object>> Evolve(
            List<List<Object>> population,
            Integer steps,
            Integer cores,
            Double pmut,
            Function<List<Object>, Double> fitness,
            GeneCatalog catalog
    ) {

        // launch cores
        if (cores > 1) {

            var res = RunInParalell(cores, () -> RunGenerations(population, steps, catalog, pmut, fitness));

            return res.stream()
                    .map( (future) -> {

                        try {
                            return future.get();
                        } catch (Exception e) {
                            return null;
                        }

                    })
                    .collect(Collectors.toList());


        } else {
            //single local process
            var ret = new ArrayList<List<Object>>();
            ret.add(RunGenerations(population, steps, catalog, pmut, fitness));
            return ret;
        }
    }



}
