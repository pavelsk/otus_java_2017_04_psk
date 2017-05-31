package test;

import java.util.List;
import java.util.Map;

public class TestObjectBuilder {
    private double doubleValue;
    private long longValue;
    private boolean booleanValue;
    private char charValue;
    private String stringValue;
    private int[] intArray;
    private String[] stringArray;
    private List list;
    private Map map;

    public TestObjectBuilder setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public TestObjectBuilder setLongValue(long longValue) {
        this.longValue = longValue;
        return this;
    }

    public TestObjectBuilder setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public TestObjectBuilder setCharValue(char charValue) {
        this.charValue = charValue;
        return this;
    }

    public TestObjectBuilder setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public TestObjectBuilder setIntArray(int[] intArray) {
        this.intArray = intArray;
        return this;
    }

    public TestObjectBuilder setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
        return this;
    }

    public TestObjectBuilder setList(List list) {
        this.list = list;
        return this;
    }

    public TestObjectBuilder setMap(Map map) {
        this.map = map;
        return this;
    }

    public TestObject createTestObject() {
        return new TestObject(doubleValue, longValue, booleanValue, charValue, stringValue, intArray, stringArray,
                list, map);
    }
}