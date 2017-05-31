package xjson.classes;

public class WrapperSerializer extends ClassSerializer<String> {

    public WrapperSerializer(String object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%s", this.object));
    }
}
