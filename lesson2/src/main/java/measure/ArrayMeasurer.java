package measure;

public abstract class ArrayMeasurer implements Measurer {
    static final int SIZE = 5 * 1024 * 1024;

    static Object[] array;

    private long getMemoryUsage() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    private void callGC() {
        try {
            System.gc();
            Thread.sleep(500);
            System.runFinalization();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void allocateArrayMemory() {
        array = new Object[SIZE];
        this.callGC();
    }

    private void releaseArrayMemory() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        array = null;
        this.callGC();
    }

    public int measure() throws InterruptedException {

        this.allocateArrayMemory();

        long initialMemory = this.getMemoryUsage();

        this.collect();

        this.callGC();

        long usedMemory = this.getMemoryUsage();

        this.releaseArrayMemory();

        return (int) Math.ceil((double) (usedMemory - initialMemory) / SIZE);
    }

    protected abstract void collect();
}
