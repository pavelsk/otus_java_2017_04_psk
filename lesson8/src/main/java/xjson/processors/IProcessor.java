package xjson.processors;

import xjson.classes.ClassSerializer;

public interface IProcessor {
    ClassSerializer process(String name, String type, Object value);
}
