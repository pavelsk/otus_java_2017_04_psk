package objects;

/**
 * Created by pavel on 14.04.17.
 */
public class ObjectArrayFactory implements ObjectFactory {

    private int length;

    public ObjectArrayFactory(int length) {
        this.length = length;
    }

    public Object createObject() {
        return new Object[this.length];
    }
}
