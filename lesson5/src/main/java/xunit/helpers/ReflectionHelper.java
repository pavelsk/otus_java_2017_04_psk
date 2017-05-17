package xunit.helpers;

import xunit.annotations.After;
import xunit.annotations.Before;
import xunit.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tully.
 */
@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getConstructor(toClasses(args)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        try {
            Method method = object.getClass().getDeclaredMethod(name, toClasses(args));
            return callMethod(object, method, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object callMethod(Object object, Method method, Object... args) {
        boolean isAccessible = true;
        try {
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    public static Method[] getTestMethods(Class<?> type, Class<?> annotationClass) {
        List<Method> methods = new ArrayList<>();

        for (Method method : type.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotationClass.isInstance(annotation)) {
                    methods.add(method);
                }
            }
        }

        return methods.toArray(new Method[methods.size()]);
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args).map(Object::getClass).collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
