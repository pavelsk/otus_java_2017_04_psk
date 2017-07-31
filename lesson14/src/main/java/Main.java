import helpers.MergeSort;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] array = generateArray();
        long start = System.currentTimeMillis();
        Arrays.parallelSort(array);
        System.out.println("Arrays.parallelSort:");
        System.out.println((System.currentTimeMillis() - start) + "ms");

        array = generateArray();
        start = System.currentTimeMillis();
        MergeSort.sort(array);
        System.out.println("Merge.sort:");
        System.out.println((System.currentTimeMillis() - start) + "ms");

        array = generateArray();
        start = System.currentTimeMillis();
        MergeSort.parallelSort(array);
        System.out.println("Merge.parallelSort");
        System.out.println((System.currentTimeMillis() - start) + "ms");
    }

    private static int[] generateArray() {
        Random random = new Random();
        int[] array = new int[2_000_000];
        for (int i = 0; i < 2_000_000; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
