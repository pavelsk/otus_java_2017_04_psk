package xjson;

import helpers.ReflectionHelper;
import xjson.classes.*;
import xjson.processors.ProcessorFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class JsonSerializer implements ISerializer {
    @Override
    public String serialize(Object o) {
        if (o == null) {
            return null;
        }

        Field[] fields = ReflectionHelper.getFields(o);
        ClassSerializer classSerializer = buildClassSerializers(o, fields);

        if (classSerializer == null) {
            return null;
        }

        StringJoiner sj = new StringJoiner(",", "{", "}");
        classSerializer.serialize(sj);

        return sj.toString();
    }

    private ClassSerializer buildClassSerializers(Object o, Field[] fields) {
        ClassSerializer first = null;
        ClassSerializer prev = null;

        for (Field field : fields) {
            ClassSerializer next = ProcessorFactory.build(o, field);

            if (first == null) {
                first = next;
                prev = next;
            } else if (next != null) {
                prev.setNext(next);
                prev = next;
            }
        }

        return first;
    }
}
