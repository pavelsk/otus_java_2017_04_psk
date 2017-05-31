package xjson.classes;

public class IntSerializer extends ClassSerializer<Integer> {

    public IntSerializer(Integer object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%d", this.object));
    }
}
