package objects;

/**
 * Created by pavel on 14.04.17.
 */
public class StringFactory implements ObjectFactory {
    public Object createObject() {
        return new String(new char[0]);
    }
}
