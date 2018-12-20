package ProblemData;

import java.util.List;

public class ContainerProblem {

    public static final int MAX_WEIGHT = 315;
    public static final int STEPS = 100_000;
    public static final int MAX_CONTAINERS = 30;

    public static Double totalWeight(List<Container> indiv) {
        var ret = 0.0;
        for(Container container : indiv) {
            ret += container.weight;
        }
        return ret;
    }

    public static Double totalPrice(List<Container> indiv) {
        var ret = 0.0;
        for(Container container : indiv) {
            ret += container.weight * container.price;
        }
        return ret;
    }

    public static Double Fitness(List<Container> indiv) {
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
