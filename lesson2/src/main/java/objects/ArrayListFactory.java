package objects;

import java.util.ArrayList;

/**
 * Created by pavel on 14.04.17.
 */
public class ArrayListFactory implements ObjectFactory {
    public Object createObject() {
        return new ArrayList<Object>();
    }
}
