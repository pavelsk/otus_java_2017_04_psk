package cache;

import app.CacheEngine;
import app.MessageSystemContext;
import messageSystem.Address;
import messageSystem.Addressee;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V>, Addressee {
    private static final int TIME_THRESHOLD_MS = 5;

    private final Address address = new Address();
    private final MessageSystemContext context;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;

    private final Map<K, CacheElement<V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(MessageSystemContext context, int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.context = context;
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
    }

    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, new CacheElement<V>(value));

        if (lifeTimeMs != 0) {
            TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
            timer.schedule(lifeTimerTask, lifeTimeMs);
        }
        if (idleTimeMs != 0) {
            TimerTask idleTimerTask = getTimerTask(key, vCacheElement -> vCacheElement.getLastAccessTime() + idleTimeMs);
            timer.schedule(idleTimerTask, idleTimeMs);
        }
    }

    public V get(K key) {
        CacheElement<V> element = elements.get(key);
        V value = null;

        if (element != null) {
            value = element.getReference().get();
            if (value != null) {
                hit++;
                element.setAccessed();
            } else {
                elements.remove(key);
            }
        } else {
            miss++;
        }
        return value;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<V> checkedElement = elements.get(key);
                if (checkedElement == null ||
                        isT1BeforeT2(timeFunction.apply(checkedElement), checkedElement.getCurrentTime())) {
                    elements.remove(key);
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
