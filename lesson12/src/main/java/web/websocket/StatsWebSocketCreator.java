package web.websocket;

import cache.CacheEngine;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v.chibrikov
 */
public class StatsWebSocketCreator implements WebSocketCreator {
    private Set<StatsWebSocket> users;

    private final CacheEngine cacheEngine;

    public StatsWebSocketCreator(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<StatsWebSocket, Boolean>());
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        StatsWebSocket socket = new StatsWebSocket(users, cacheEngine);
        System.out.println("Socket created");
        return socket;
    }
}
