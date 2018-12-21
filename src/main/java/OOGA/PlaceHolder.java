package OOGA;

public class PlaceHolder<T> {

    private T item;

    public PlaceHolder(T item){
        this.item = item;
    }

    public T get(){
        return this.item;
    }
}
