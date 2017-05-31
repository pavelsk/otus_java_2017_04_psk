package xjson.processors;

import xjson.classes.ClassSerializer;
import xjson.classes.WrapperSerializer;

import java.lang.reflect.Array;
import java.util.StringJoiner;

public class ArrayProcessor implements IProcessor {
    @Override
    public ClassSerializer process(String name, String type, Object value) {
        if (value == null) {
            return null;
        }

        ClassSerializer classSerializer = null;
        ClassSerializer prev = null;

        String realType = type.replaceFirst("\\[\\]", "");

        Object[] array = new Object[Array.getLength(value)];
        for (int i = 0; i < array.length; i++) {
            ClassSerializer nextSerializer = ProcessorFactory.build(null, realType, Array.get(value, i));

            if (classSerializer == null) {
                classSerializer = nextSerializer;
                prev = nextSerializer;
            } else if (nextSerializer != null) {
                prev.setNext(nextSerializer);
                prev = nextSerializer;
            }
        }

        StringJoiner sj = new StringJoiner(",", "[", "]");
        if (classSerializer != null) {
            classSerializer.serialize(sj);
        }

        return new WrapperSerializer(sj.toString(), name);
    }
}
