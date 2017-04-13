package measure;

import objects.ObjectFactory;

public class MeasurerFactory {
    public static Measurer getMeasurer(Class clazz) {
        return new ClassBasedMeasurer(clazz);
    }
    public static Measurer getMeasurer(ObjectFactory factory) {
        return new FactoryBasedMeasurer(factory);
    }
}
