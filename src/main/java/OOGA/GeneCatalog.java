package OOGA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static GA.RandomFunc.RandFrom;

public class GeneCatalog {

    //private ArrayList<List<Object>> list;

    private ArrayList<Supplier<Object>> sources;

    public GeneCatalog(){
        this.sources = new ArrayList<>();
    }

    public GeneCatalog createSourceOf(Object ... objs){
        this.sources.add(() -> RandFrom( Arrays.asList(objs) ));
        return this;
    }

    public GeneCatalog addSource(List<Object> src){
        this.sources.add(() -> RandFrom(src));
        return this;
    }

    public GeneCatalog addGenerator(Supplier<Object> src) {
        this.sources.add(src);
        return this;
    }

    public GeneCatalog addSourceWithSpan(int nSlots, List src){
        for (int i = 0; i < nSlots ; i++) {
            addSource(src);
        }
        return this;
    }

    /*
    public List<Object> getFor(int slot) {
        if (slot < this.list.size()) return this.list.get(slot);
        else return null;
    }
    */

    public Object randForSlot(int slot) {
        return this.sources.get(slot).get();
    }

    public int nSources(){
        return this.sources.size();
    }
}
