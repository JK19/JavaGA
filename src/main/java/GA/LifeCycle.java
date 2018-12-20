package GA;


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
    public static <T> Tuple<List<List<T>>, List<T>> PopulationStep(
            List<List<T>> population,
            List<T> alphabet,
            double pmut,
            Function<List<T>, Double> fitnessF
    ){

        // local copies
        population = new ArrayList<>(population);


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
        population.addAll( RandPopFrom(alphabet, adnlen, poplen-2) );

        // mutate population
        population = population.stream()
                .map( indiv -> Mutate(indiv, pmut, alphabet) )
                .collect(Collectors.toList());

        return new Tuple(population, best);
    }




    public static <T> List<T> RunGenerations(
            List<List<T>> population,
            int steps,
            List<T> alphabet,
            Double pmut,
            Function<List<T>, Double> fitness
    ) {

        // local copy
        population = new ArrayList<>(population);

        // most performant indivs
        List<T> top = null;
        List<T> best = null;

        //var log = Logger.getLogger("RunGenerations");

        for (int step = 0; step < steps ; step++) {

            var res = PopulationStep(population, alphabet, pmut, fitness);

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
    public static <T> List<List<T>> Evolve(
            List<List<T>> population,
            Integer steps,
            Integer cores,
            Double pmut,
            Function<List<T>, Double> fitness,
            List<T> geneList
    ) {

        // launch cores
        if (cores > 1) {

            var res = RunInParalell(cores, () -> RunGenerations(population, steps, geneList, pmut, fitness));

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
            var ret = new ArrayList<List<T>>();
            ret.add(RunGenerations(population, steps, geneList, pmut, fitness));
            return ret;
        }
    }



}
