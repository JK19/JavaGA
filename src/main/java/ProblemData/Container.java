package ProblemData;

public class Container {
    public final String tag;
    public final Integer price;
    public final Integer weight;

    public Container(String tag, Integer price, Integer weight) {
        this.tag = tag;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
