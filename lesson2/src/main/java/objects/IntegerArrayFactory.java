package objects;

/**
 * Created by pavel on 14.04.17.
 */
public class IntegerArrayFactory implements ObjectFactory {

    private int length;

    public IntegerArrayFactory(int length) {
        this.length = length;
    }

    public Object createObject() {
        return new int[this.length];
    }
}
