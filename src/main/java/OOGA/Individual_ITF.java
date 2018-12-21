package OOGA;

import java.util.function.Consumer;

public interface Individual_ITF {

    Individual_ITF foreach(Consumer<PlaceHolder> f);
    boolean replace(int idx, PlaceHolder ph);
    Individual_ITF addSlot(PlaceHolder ph);

}
