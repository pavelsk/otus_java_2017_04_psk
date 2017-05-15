package xunit.assertion;

public class Assert {
    public static void assertTrue(String message, boolean condition) {
        if (!condition) {
            assertFail(message);
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            assertFail(null);
        }
    }

    public static void assertEquals(Object object1, Object object2) {
        assertTrue(null, object1.equals(object2));
    }

    public static void assertEquals(String message, Object object1, Object object2) {
        assertTrue(message, object1.equals(object2));
    }

    public static void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    public static void assertNotNull(Object object) {
        assertTrue(object != null);
    }

    public static void assertFail(String message) {
        if (message == null) {
            throw new RuntimeException();
        } else {
            throw new RuntimeException(message);
        }
    }
}
