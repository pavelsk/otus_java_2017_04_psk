package xjson.classes;

public class ShortSerializer extends ClassSerializer<Short> {

    public ShortSerializer(Short object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%d", this.object));
    }
}
