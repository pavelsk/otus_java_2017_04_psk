package helpers;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Object getFieldValue(Object object, Field field) {
        boolean isAccessible = true;
        try {
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static Field[] getFields(Object object) {
        return object.getClass().getDeclaredFields();
    }
}
