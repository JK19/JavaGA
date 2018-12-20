package GA;

import GA.LifeCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Runner {

    /*
    public static <T> List<T> loop(List<List<T>> population, int steps, List<T> alphabet, Double pmut, Function<List<T>, Double> fitness) {

        // local copy
        List<List<T>> pop = new ArrayList<>(population);

        List<T> top = null;
        List<T> best = null;

        for (int step = 0; step < steps ; step++) {

            var res = LifeCycle.cycle(pop, alphabet, pmut, fitness);

            // log generation

            pop = res.value1;
            best = res.value2;

            if (top == null) top = res.value2;

            if (fitness.apply(best) > fitness.apply(top)) {
                top = best;
            }
        }

        return top;
    }

    public static <T> List<Future<List<T>>> runInParalell(
            List<List<T>> population,
            int steps,
            Double pmut,
            List<T> alphabet,
            Function<List<T>, Double> fitness
    ) throws InterruptedException {
        var executor = Executors.newWorkStealingPool(4);

        Callable<List<T>> task = () -> loop(population, steps, alphabet, pmut, fitness);

        var tasks = Collections.nCopies(population.size(), task);

        return executor.invokeAll(tasks);
    }
    */

    public static <T> List<Future<T>> RunInParalell(int cores, Callable<T> task){
        var executor = Executors.newFixedThreadPool(cores);

        var tasks = Collections.nCopies(cores, task);

        try {
            var res = executor.invokeAll(tasks);
            executor.shutdown();
            return res;
        } catch (InterruptedException ex) {
            executor.shutdown();
            return null;
        }
    }
}
