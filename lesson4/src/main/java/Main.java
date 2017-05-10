import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = Integer.parseInt(args[0]);
        int duration = Integer.parseInt(args[1]);

        GCInfo gcInfo = new GCInfo();
        gcInfo.startListeners();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);

        mbean.setSize(size);
        mbean.setDuration(duration);
        mbean.run();

        gcInfo.stopListeners();
        System.out.println(gcInfo.getStatistics());
    }
}
