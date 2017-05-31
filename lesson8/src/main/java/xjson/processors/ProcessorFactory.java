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

        switch (type) {
            case "double":
            case "java.lang.double":
                return new DoubleSerializer((Double) value, name);
            case "float":
            case "java.lang.float":
                return new FloatSerializer((Float) value, name);
            case "long":
            case "java.lang.long":
                return new LongSerializer((Long) value, name);
            case "int":
            case "java.lang.integer":
                return new IntSerializer((Integer) value, name);
            case "short":
            case "java.lang.short":
                return new ShortSerializer((Short) value, name);

            case "char":
            case "java.lang.character":
                return new CharSerializer((Character) value, name);

            case "boolean":
            case "java.lang.boolean":
                return new BooleanSerializer((Boolean) value, name);

            case "string":
            case "java.lang.string":
                return new StringSerializer((String) value, name);
        }

        return null;
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
