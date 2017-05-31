package xjson.processors;

import xjson.classes.ClassSerializer;
import xjson.classes.WrapperSerializer;

import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class ListProcessor implements IProcessor {
    @Override
    public ClassSerializer process(String name, String type, Object value) {
        if (value == null) {
            return null;
        }

        ClassSerializer classSerializer = null;
        ClassSerializer prev = null;

        Iterator iterator = ((List) value).iterator();
        while (iterator.hasNext()) {
            Object iteratorValue = iterator.next();
            ClassSerializer nextSerializer =
                    ProcessorFactory.build(null, iteratorValue.getClass().getTypeName().toLowerCase(), iteratorValue);

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
