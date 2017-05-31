package xjson.classes;

public class FloatSerializer extends ClassSerializer<Float> {

    public FloatSerializer(Float object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%s", this.object));
    }
}
