package xjson.classes;

public class BooleanSerializer extends ClassSerializer<Boolean> {

    public BooleanSerializer(Boolean object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%b", this.object));
    }
}
