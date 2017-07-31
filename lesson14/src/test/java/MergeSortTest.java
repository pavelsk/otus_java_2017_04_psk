import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import helpers.MergeSort;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Random;

@RunWith(DataProviderRunner.class)
public class MergeSortTest {

    @DataProvider
    public static Object[][] generateArray() {
        Random random = new Random();
        int[] array = new int[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = random.nextInt();
        }

        return new Object[][] {
                {array}
        };
    }

    @Test
    @UseDataProvider("generateArray")
    public void testSort(int[] array) {
        int[] arrayCopy = Arrays.copyOf(array, array.length);

        Arrays.sort(arrayCopy);

        MergeSort.sort(array);

        Assert.assertArrayEquals(array, arrayCopy);
    }

    @Test
    @UseDataProvider("generateArray")
    public void testParallelSort(int[] array) {
        int[] arrayCopy = Arrays.copyOf(array, array.length);

        Arrays.sort(arrayCopy);

        MergeSort.parallelSort(array);

        Assert.assertArrayEquals(array, arrayCopy);
    }
}
