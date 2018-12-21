package GA;

import OOGA.GeneCatalog;

import javax.xml.catalog.Catalog;

import static GA.RandomFunc.*;

import java.util.ArrayList;
import java.util.List;

public class GeneManipulation {

    // Modifies individuals with a given probability replacing a single random
    // element with one from supplied alphabet
    // indiv : list to modify
    // pmut : probability of mutation
    // alphabet : list of new genes available
    // returns : a new mutated individual
    public static List<Object> Mutate(List<Object> indiv, double pmut, GeneCatalog catalog){

        // local copy
        indiv = new ArrayList<>(indiv);

        // only mutate if random event occur
        if( RandDouble() < pmut) {
            int randPoint = RandRangeInt(0, indiv.size()-1);
            indiv.set(randPoint, catalog.randForSlot(randPoint));
        }

        return indiv;
    }

    // Exchanges elements between lists at a common random point
    // Doesn't modifiy the lists passed as arguments
    // a : list of elements
    // b : list of elements
    // returns : Tuple with the two resulting lists
    public static Tuple<List<Object>, List<Object>> Crossover(List<Object> a, List<Object> b) {

        if(a.size() != b.size()) return null; // error reporting by returning null

        // Copy values
        //var a_copy = new ArrayList<>(a);
        a = new ArrayList<>(a);
        //var b_copy = new ArrayList<>(b);
        b = new ArrayList<>(b);

        // Length
        int len = a.size();

        // Random point
        int cutPoint = RandRangeInt(0, len-1);

        // to exchange two parts a third is needed as tmp storage
        List<Object> tmp = new ArrayList<>(a.subList(cutPoint, len));

        a.subList(cutPoint, len).clear(); // clear tail
        a.addAll(cutPoint, b.subList(cutPoint, len)); // append b's tail

        b.subList(cutPoint, len).clear(); // clear tail
        b.addAll(cutPoint, tmp); // append a's tail

        // return as tuple
        return new Tuple(a, b);
    }


}
