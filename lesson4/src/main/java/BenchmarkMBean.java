/**
 *
 */
public interface BenchmarkMBean {
    int getSize();

    void setSize(int size);

    int getDuration();

    void setDuration(int duration);

    boolean isRunning();

    void setRunning(boolean running);
}
