package xjson.classes;

public class StringSerializer extends ClassSerializer<String> {

    public StringSerializer(String object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("\"%s\"", this.object));
    }
}
