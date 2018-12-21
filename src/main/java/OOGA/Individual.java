package OOGA;

import java.util.ArrayList;
import java.util.List;

public class Individual {

    private List<PlaceHolder> list;

    public Individual(){
        this.list = new ArrayList<PlaceHolder>();
    }

    public void addPlaceHolder(PlaceHolder ph){
        this.list.add(ph);
    }
}