package web.websocket;

import cache.CacheEngine;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

import java.util.Set;

@WebSocket
public class StatsWebSocket {
    private Set<StatsWebSocket> users;
    private CacheEngine cacheEngine;
    private Session session;

    public StatsWebSocket(Set<StatsWebSocket> users, CacheEngine cacheEngine) {
        this.users = users;
        this.cacheEngine = cacheEngine;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        try {
            if (data.equals("update")) {
                JSONObject object = new JSONObject();
                object.put("hitCount", cacheEngine.getHitCount());
                object.put("missCount", cacheEngine.getMissCount());

                session.getRemote().sendString(object.toJSONString());
                System.out.println("Sending message: " + data);
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);
        System.out.println("onOpen");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
        System.out.println("onClose");
    }
}
