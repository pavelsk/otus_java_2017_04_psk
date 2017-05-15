package xunit;

import xunit.helpers.ReflectionHelper;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XUnit {
    public static void test(String[] stringClasses) throws ClassNotFoundException {
        Class<?>[] classes = new Class[stringClasses.length];

        int i = 0;
        for (String clazz : stringClasses) {
            classes[i++] = ClassLoader.getSystemClassLoader().loadClass(clazz);
        }

        test(classes);
    }

    public static void test(Class<?>[] classes) {
        run(classes);
    }

    public static void test(String packageName) {
        String path = packageName.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);

        if (url == null) {
            throw new IllegalArgumentException("Пакет не найден");
        }

        File scannedDir = new File(url.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(loadClasses(file, packageName));
        }

        run(classes.toArray(new Class<?>[classes.size()]));
    }

    private static List<Class<?>> loadClasses(File file, String packageName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = packageName + '/' + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(loadClasses(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            int endIndex = resource.length() - ".class".length();
            String className = resource.substring(0, endIndex).replace("/", ".");
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    private static void run(Class<?>[] classes) {
        int count = 0;

        for (Class<?> clazz : classes) {
            Method beforeMethod = ReflectionHelper.getBeforeMethod(clazz);
            Method afterMethod = ReflectionHelper.getAfterMethod(clazz);
            Method[] testMethods = ReflectionHelper.getTestMethods(clazz);

            for (Method method : testMethods) {
                Object instance = ReflectionHelper.instantiate(clazz);

                if (beforeMethod != null) {
                    ReflectionHelper.callMethod(instance, beforeMethod);
                }

                ReflectionHelper.callMethod(instance, method);

                if (afterMethod != null) {
                    ReflectionHelper.callMethod(instance, afterMethod);
                }

                count++;
            }
        }

        System.out.println(String.format("OK. %d tests passed", count));
    }
}
