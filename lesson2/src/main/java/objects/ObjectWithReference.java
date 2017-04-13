package objects;

/**
 * Created by pavel on 12.04.17.
 */
public class ObjectWithReference {
    private Object value = new Object();

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
