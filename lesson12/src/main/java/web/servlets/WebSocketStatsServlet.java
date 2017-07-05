package web.servlets;

import cache.CacheEngine;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import web.websocket.StatsWebSocketCreator;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketStatsServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    private final CacheEngine cacheEngine;

    public WebSocketStatsServlet(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new StatsWebSocketCreator(this.cacheEngine));
    }
}
