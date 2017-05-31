package xjson.classes;

import java.util.StringJoiner;

public abstract class ClassSerializer<T> {
    private ClassSerializer next;

    protected T object;

    protected final String fieldName;

    protected ClassSerializer(T object, String fieldName) {
        this.object = object;
        this.fieldName = fieldName;
    }

    public ClassSerializer getNext() {
        return next;
    }

    public void setNext(ClassSerializer next) {
        this.next = next;
    }

    public void serialize(StringJoiner join) {
        String value = this.handle();

        if (value != null) {
            join.add(value);
        }

        if (getNext() != null) {
            getNext().serialize(join);
        }
    }

    protected abstract String handle();

    protected String format(String s) {
        return this.fieldName != null ? String.format("\"%s\":%s", this.fieldName, s) : s;
    }
}
