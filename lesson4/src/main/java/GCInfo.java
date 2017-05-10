import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GCInfo {
    private List<Runnable> listeners;

    private long time;

    private long youngGCCount;

    private long oldGCCount;

    private long startTime;

    private long stopTime;

    public void stopListeners() {
        stopTime = Calendar.getInstance().getTime().getTime();
        listeners.forEach(Runnable::run);
        listeners = null;
    }

    public void startListeners() {
        this.time = 0;
        this.youngGCCount = 0L;
        this.oldGCCount = 0L;
        this.startTime = Calendar.getInstance().getTime().getTime();

        listeners = new ArrayList<>();

        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        gcBeans.forEach(garbageCollectorMXBean -> {
            NotificationEmitter emitter = (NotificationEmitter) garbageCollectorMXBean;

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(
                        GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION
                )) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(
                            (CompositeData) notification.getUserData()
                    );

                    time += info.getGcInfo().getDuration();

                    String gctype = info.getGcAction();
                    if ("end of minor GC".equals(gctype)) {
                        this.youngGCCount++;
                    } else if ("end of major GC".equals(gctype)) {
                        this.oldGCCount++;
                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);

            listeners.add(() -> {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public String getStatistics() {
        long time = (stopTime - startTime) / 1000;

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Количество сборок young gen: %d \n", this.youngGCCount));
        sb.append(String.format("Количество сборок old gen: %d \n", this.oldGCCount));
        sb.append(String.format("Сборок в минуту: %.2f \n", (this.youngGCCount + this.oldGCCount) / (time / 60.0)));
        sb.append(String.format("Суммарное время сборок сборок: %d мс \n", this.time));

        return sb.toString();
    }
}
