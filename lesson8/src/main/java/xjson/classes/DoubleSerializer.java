package xjson.classes;

public class DoubleSerializer extends ClassSerializer<Double> {

    public DoubleSerializer(Double object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%s", this.object));
    }
}
