package measure;

import objects.ObjectFactory;

public class FactoryBasedMeasurer extends ArrayMeasurer {

    private ObjectFactory factory;

    public FactoryBasedMeasurer(ObjectFactory factory) {
        this.factory = factory;
    }

    protected void collect() {
        int n = 0;
        while (n < SIZE) {
            try {
                array[n] = factory.createObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            n++;
        }
    }
}
