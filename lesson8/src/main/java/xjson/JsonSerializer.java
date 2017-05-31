package xjson;

import helpers.ReflectionHelper;
import xjson.classes.*;
import xjson.processors.ProcessorFactory;

import java.lang.reflect.Field;
import java.util.*;

public class JsonSerializer implements ISerializer {
    @Override
    public String serialize(Object o) {
        if (o == null) {
            return null;
        }

        // Проверяем базовые типы
        String type = o.getClass().getTypeName().toLowerCase();
        ClassSerializer serializer = ProcessorFactory.build(null, type, o);

        if (serializer != null) {
            StringJoiner joiner = new StringJoiner("", "", "");
            serializer.serialize(joiner);
            return joiner.toString();
        }

        // Для кастомных классов орудуем рефлексией
        Field[] fields = ReflectionHelper.getFields(o);
        ClassSerializer classSerializer = buildClassSerializers(o, fields);

        if (classSerializer == null) {
            return "{}";
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
