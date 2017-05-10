import java.util.Random;

public class Benchmark implements BenchmarkMBean {

    private int size;

    private boolean running = true;

    private int duration;

    void run() throws InterruptedException {
        this.startTimer();
        this.startMemoryManipulations();
    }

    private void startTimer() {
        new Thread(() -> {
            try {
                Thread.sleep(this.duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.running = false;
            }
        }).start();
    }

    private void startMemoryManipulations() throws InterruptedException {
        Object[] array = new Object[size];

        Random random = new Random(this.size);

        int n = 0;
        int currentSize = size;
        while (running) {
            int i = n % currentSize;
            array[i] = new String(new char[0]);
            n++;
            if (n % currentSize == 0) {
                currentSize = size;
                array = new Object[currentSize];
            }
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }
}
