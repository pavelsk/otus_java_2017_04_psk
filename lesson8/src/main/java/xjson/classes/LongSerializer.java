package xjson.classes;

public class LongSerializer extends ClassSerializer<Long> {

    public LongSerializer(Long object, String fieldName) {
        super(object, fieldName);
    }

    @Override
    protected String handle() {
        return this.format(String.format("%d", this.object));
    }
}
