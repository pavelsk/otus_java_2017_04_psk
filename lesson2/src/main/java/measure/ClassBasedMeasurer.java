package measure;

import java.lang.reflect.Constructor;

public class ClassBasedMeasurer extends ArrayMeasurer {

    private Class clazz;

    public ClassBasedMeasurer(Class clazz) {
        this.clazz = clazz;
    }

    private Constructor getConstructor() {
        try {
            return this.clazz.getConstructor(new Class[]{});
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    protected void collect() {
        Constructor constructor = this.getConstructor();
        if (constructor == null) {
            return;
        }

        int n = 0;
        while (n < SIZE) {
            try {
                array[n] = clazz.cast(constructor.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            n++;
        }
    }
}
