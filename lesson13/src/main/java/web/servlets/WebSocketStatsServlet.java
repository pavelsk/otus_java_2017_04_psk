package web.servlets;

import cache.CacheEngine;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import web.websocket.StatsWebSocketCreator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketStatsServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext context = (ApplicationContext) config.getServletContext()
                .getAttribute("applicationContext");
        CacheEngine cache = (CacheEngine) context.getBean("cache");

        WebSocketServletFactory factory = (WebSocketServletFactory) config.getServletContext()
                .getAttribute(WebSocketServletFactory.class.getName());
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new StatsWebSocketCreator(cache));
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {

    }
}
