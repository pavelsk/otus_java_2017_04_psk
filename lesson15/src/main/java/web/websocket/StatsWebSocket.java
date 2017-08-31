package web.websocket;

import app.FrontendService;
import app.Socket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Set;

@WebSocket
public class StatsWebSocket implements Socket {
    private Set<StatsWebSocket> users;
    private FrontendService frontendService;
    private Session session;

    private volatile boolean isALive;

    public StatsWebSocket(Set<StatsWebSocket> users, FrontendService frontendService) {
        this.users = users;
        this.frontendService = frontendService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        try {
            if (data.equals("update")) {
                frontendService.handleStatsRequest(this);
                this.sendMessage("updating");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.isALive = true;
        users.add(this);
        setSession(session);
        System.out.println("onOpen");
    }

    public void sendMessage(String messase) {
        try {
            if (isALive) {
                session.getRemote().sendString(messase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.isALive = false;
        users.remove(this);
        System.out.println("onClose");
    }
}
