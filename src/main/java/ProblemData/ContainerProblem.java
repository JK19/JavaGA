package ProblemData;

import java.util.List;

public class ContainerProblem {

    public static final int MAX_WEIGHT = 315;
    public static final int STEPS = 100_000;
    public static final int MAX_CONTAINERS = 30;

    public static Double totalWeight(List<Object> indiv) {
        var ret = 0.0;
        for(Object container : indiv) {
            ret += ((Container) container).weight;
        }
        return ret;
    }

    public static Double totalPrice(List<Object> indiv) {
        var ret = 0.0;
        for(Object container : indiv) {
            ret += ((Container) container).weight * ((Container) container).price;
        }
        return ret;
    }

    public static Double Fitness(List<Object> indiv) {
        var totalw = totalWeight(indiv);
        var totalp = totalPrice(indiv);

        if(totalw <= MAX_WEIGHT) { // if under max weight return it's value
            return totalp * totalw; // multiply so the fitness scales linearly
        } else { // return it's owerweight as negative value
            var overW = totalw - MAX_WEIGHT;
            return -overW;
        }
    }
}
