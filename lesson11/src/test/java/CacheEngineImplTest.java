import cache.CacheEngine;
import cache.CacheEngineImpl;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheEngineImplTest {
    static class BigObject {
        final byte[] array = new byte[1024 * 1024];

        public byte[] getArray() {
            return array;
        }
    }

    @Test
    public void maxElementsTest() {
        CacheEngine<Integer, Integer> cacheEngine = new CacheEngineImpl<>(5, 0, 0);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.put(i, i);
        }

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.get(i);
        }

        assertEquals(5, cacheEngine.getHitCount());
        assertEquals(5, cacheEngine.getMissCount());
    }

    @Test
    public void lifeTimeTest() throws InterruptedException {
        CacheEngine<Integer, Integer> cacheEngine = new CacheEngineImpl<>(5, 100, 0);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.put(i, i);
        }

        TimeUnit.MILLISECONDS.sleep(60);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.get(i);
        }

        assertEquals(5, cacheEngine.getHitCount());
        assertEquals(5, cacheEngine.getMissCount());

        TimeUnit.MILLISECONDS.sleep(60);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.get(i);
        }

        assertEquals(5, cacheEngine.getHitCount());
        assertEquals(15, cacheEngine.getMissCount());
    }

    @Test
    public void idleTimeTest() throws InterruptedException {
        CacheEngine<Integer, Integer> cacheEngine = new CacheEngineImpl<>(5, 0, 100);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.put(i, i);
        }

        TimeUnit.MILLISECONDS.sleep(60);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.get(i);
        }

        assertEquals(5, cacheEngine.getHitCount());
        assertEquals(5, cacheEngine.getMissCount());

        TimeUnit.MILLISECONDS.sleep(60);

        for (int i = 1; i <= 10; ++i) {
            cacheEngine.get(i);
        }

        assertEquals(10, cacheEngine.getHitCount());
        assertEquals(10, cacheEngine.getMissCount());
    }

    /**
     * -Xms256m -Xmx256m
     */
    @Test
    public void softLinkTest() {
        int size = 500;
        CacheEngine<Integer, BigObject> cacheEngine = new CacheEngineImpl<>(size, 0, 0);

        for (int i = 0; i < size; i++) {
            cacheEngine.put(i, new BigObject());
        }

        int sum = 0;
        for (int k = 0; k < size; k++) {
            if (cacheEngine.get(k) != null) sum++;
        }

        assertTrue(sum < size);
    }
}
