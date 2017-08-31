package web.listeners;

import app.MessageSystemContext;
import messageSystem.Addressee;
import messageSystem.MessageSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import workers.DBWorkerService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    private DBWorkerService dbWorkerService;

    private MessageSystem messageSystem;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        servletContextEvent.getServletContext().setAttribute("applicationContext", context);

        dbWorkerService = (DBWorkerService) context.getBean("db_worker");
        dbWorkerService.start();

        messageSystem = (MessageSystem) context.getBean("message_system");
        messageSystem.start();

        Addressee cacheEngine = (Addressee) context.getBean("cache");
        Addressee frontendService = (Addressee) context.getBean("frontend_service");

        MessageSystemContext messageSystemContext = (MessageSystemContext) context.getBean("message_system_context");
        messageSystemContext.setFrontAddress(frontendService.getAddress());
        messageSystemContext.setCacheAddress(cacheEngine.getAddress());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        dbWorkerService.stop();
        messageSystem.stop();
    }
}
