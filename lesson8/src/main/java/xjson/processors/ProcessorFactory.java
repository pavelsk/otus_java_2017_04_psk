package xjson.processors;

import helpers.ReflectionHelper;
import xjson.classes.*;

import java.lang.reflect.Field;

public class ProcessorFactory {
    public static ClassSerializer build(Object object, Field field) {
        String type = field.getType().getSimpleName().toLowerCase();
        String name = field.getName();
        Object value = ReflectionHelper.getFieldValue(object, field);

        return build(name, type, value);
    }

    public static ClassSerializer build(String name, String type, Object value) {
        if (isTypeArray(type)) {
            return new ArrayProcessor().process(name, type, value);
        } else if (isTypeList(type)) {
            return new ListProcessor().process(name, type, value);
        } else if (isTypeMap(type)) {
            return new MapProcessor().process(name, type, value);
        }

        return new BaseTypesProcessor().process(name, type, value);
    }

    private static boolean isTypeArray(String type) {
        return type.contains("[]");
    }

    private static boolean isTypeList(String type) {
        return type.contains("list");
    }

    private static boolean isTypeMap(String type) {
        return type.contains("map");
    }
}
