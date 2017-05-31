package test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestObject {
    private double doubleValue;
    private long longValue;
    private boolean booleanValue;
    private char charValue;

    private String stringValue;

    private int[] intArray;

    private String[] stringArray;

    private List list;

    private Map map;

    public TestObject(
            double doubleValue,
            long longValue,
            boolean booleanValue,
            char charValue,
            String stringValue,
            int[] intArray,
            String[] stringArray,
            List list,
            Map map
    ) {
        this.doubleValue = doubleValue;
        this.longValue = longValue;
        this.booleanValue = booleanValue;
        this.charValue = charValue;
        this.stringValue = stringValue;
        this.intArray = intArray;
        this.stringArray = stringArray;
        this.list = list;
        this.map = map;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "doubleValue=" + doubleValue +
                ", longValue=" + longValue +
                ", booleanValue=" + booleanValue +
                ", charValue=" + charValue +
                ", stringValue='" + stringValue + '\'' +
                ", intArray=" + Arrays.toString(intArray) +
                ", stringArray=" + Arrays.toString(stringArray) +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}
