package xjson.classes;

public class CharSerializer extends ClassSerializer<Character> {

    public CharSerializer(Character object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("\"%c\"", this.object));
    }
}
