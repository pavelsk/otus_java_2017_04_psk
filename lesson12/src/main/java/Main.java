import cache.CacheEngine;
import cache.CacheEngineImpl;
import db.DBService;
import db.DBServiceHibernateImpl;
import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import web.servlets.AdminServlet;
import web.servlets.MainServlet;
import web.servlets.WebSocketStatsServlet;
import workers.InsertWorker;
import workers.SelectWorker;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        CacheEngine<String, byte[]> cacheEngine = new CacheEngineImpl<>(10, 0, 0);

        startDB(cacheEngine);
        startServer(cacheEngine);
    }

    private static void startDB(CacheEngine cacheEngine) {
        DBService dbService = new DBServiceHibernateImpl(cacheEngine);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        executor.execute(new InsertWorker(dbService));
        executor.execute(new SelectWorker(dbService));
    }

    private static void startServer(CacheEngine cacheEngine) throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);

        contextHandler.setSecurityHandler(getSecurityHandler());
        contextHandler.addServlet(AdminServlet.class, "/admin");
        contextHandler.addServlet(new ServletHolder(new WebSocketStatsServlet(cacheEngine)), "/stats");
        contextHandler.addServlet(MainServlet.class, "/");

        server.start();
        server.join();
    }

    private static SecurityHandler getSecurityHandler() {
        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(new String[]{"admin"});
        constraint.setAuthenticate(true);

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setConstraint(constraint);
        mapping.setPathSpec("/admin");

        IdentityService identService = new DefaultIdentityService();

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.addConstraintMapping(mapping);
        securityHandler.setIdentityService(identService);

        HashLoginService loginService = new HashLoginService("Admin Authentication", "lesson12/security");
        loginService.setIdentityService(identService);

        securityHandler.setLoginService(loginService);
        securityHandler.setAuthenticator(new BasicAuthenticator());

        return securityHandler;
    }
}
