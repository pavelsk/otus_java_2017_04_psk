package helpers;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeSort {
    private final static int THREADS_NUMBER = 4;

    private final static AtomicInteger threadsCounter = new AtomicInteger();

    public static void sort(int[] array) {
        Sort sort = new Sort();
        sort.sort(array, false);
    }

    public static void parallelSort(int[] array) {
        // first thread is caller thread
        threadsCounter.set(1);

        Sort sort = new Sort();
        sort.sort(array, true);
    }

    private static class Sort {
        private void sort(int[] array, boolean parallel) {
            if (array.length < 2) {
                return;
            }

            int middle = array.length / 2;
            int[] leftArray = Arrays.copyOfRange(array, 0, middle);
            int[] rightArray = Arrays.copyOfRange(array, middle, array.length);

            if (parallel && MergeSort.threadsCounter.getAndIncrement() < THREADS_NUMBER) {
                Thread thread = new Thread(() -> {
                    sort(leftArray, true);
                });
                Thread threadCurrent = new Thread(() -> {
                    sort(rightArray, true);
                });

                thread.start();
                threadCurrent.run();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                sort(leftArray, false);
                sort(rightArray, false);
            }

            int i = 0, j = 0, from = 0;
            while (i < leftArray.length || j < rightArray.length) {
                if (j == rightArray.length || (i < leftArray.length && leftArray[i] <= rightArray[j])) {
                    array[from++] = leftArray[i++];
                } else {
                    array[from++] = rightArray[j++];
                }
            }
        }
    }
}
