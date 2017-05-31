package xjson.processors;

import xjson.classes.*;

public class BaseTypesProcessor implements IProcessor {
    @Override
    public ClassSerializer process(String name, String type, Object value) {
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
}
