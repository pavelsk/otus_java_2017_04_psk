package list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.*;

public class MyArrayListTest {

    private static final int TEST_ARRAYS_SIZE = 100;

    @Test
    public void testByAddAll() {
        List<Integer> myArrayList = new MyArrayList<>();

        int additionalElements[] = {200, 300, 400};

        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            myArrayList.add(i);
        }

        Collections.addAll(myArrayList, additionalElements[0], additionalElements[1], additionalElements[2]);

        assertEquals(TEST_ARRAYS_SIZE + additionalElements.length, myArrayList.size());
        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            assertEquals(i, (int) myArrayList.get(i));
        }
        for (int i = 0; i < additionalElements.length; i++) {
            assertEquals(additionalElements[i], (int) myArrayList.get(TEST_ARRAYS_SIZE + i));
        }
    }

    @Test
    public void testByCopy() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> myArrayList = new MyArrayList<>();

        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            arrayList.add(i);
            myArrayList.add(0);
        }

        Collections.copy(myArrayList, arrayList);

        assertEquals(TEST_ARRAYS_SIZE, myArrayList.size());
        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            assertEquals(arrayList.get(i), myArrayList.get(i));
        }
    }

    @Test
    public void testBySort() {
        List<Integer> myArrayList = new MyArrayList<>();

        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            myArrayList.add(i);
        }
        Collections.shuffle(myArrayList);

        // DESC
        Collections.sort(myArrayList, (o1, o2) -> o1 > o2 ? -1 : o1 < o2 ? 1 : 0);

        assertEquals(TEST_ARRAYS_SIZE, myArrayList.size());
        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            assertEquals(TEST_ARRAYS_SIZE - i - 1, (int) myArrayList.get(i));
        }

        // ASC
        Collections.sort(myArrayList, (o1, o2) -> o1 < o2 ? -1 : o1 > o2 ? 1 : 0);

        assertEquals(TEST_ARRAYS_SIZE, myArrayList.size());
        for (int i = 0; i < TEST_ARRAYS_SIZE; i++) {
            assertEquals(i, (int) myArrayList.get(i));
        }
    }
}
