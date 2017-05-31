package xjson.processors;

import xjson.classes.ClassSerializer;
import xjson.classes.WrapperSerializer;

import java.util.Map;
import java.util.StringJoiner;

public class MapProcessor implements IProcessor {
    @Override
    public ClassSerializer process(String name, String type, Object value) {
        if (value == null) {
            return null;
        }

        StringJoiner sj = new StringJoiner(",", "{", "}");

        for (Object entry : ((Map) value).entrySet()) {
            Object key = ((Map.Entry) entry).getKey();
            Object val = ((Map.Entry) entry).getValue();
            String keyString = null;

            if (val == null) {
                continue;
            }

            if (key != null) {
                ClassSerializer nextKeySerializer =
                        ProcessorFactory.build(null, key.getClass().getTypeName().toLowerCase(), key);

                if (nextKeySerializer != null) {
                    StringJoiner keyJoiner = new StringJoiner("", "", "");
                    nextKeySerializer.serialize(keyJoiner);

                    // todo сделать лучше
                    keyString = keyJoiner.toString().replaceAll("\",\"", ", ").replaceAll("\"", "");
                }
            }

            ClassSerializer nextValSerializer =
                    ProcessorFactory.build(null, val.getClass().getTypeName().toLowerCase(), val);

            StringJoiner valJoiner = new StringJoiner("", "", "");
            nextValSerializer.serialize(valJoiner);

            sj.add("\"" + keyString + "\":" + valJoiner.toString());
        }

        return new WrapperSerializer(sj.toString(), name);
    }
}
