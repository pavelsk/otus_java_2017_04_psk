package web.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import workers.DBWorkerService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    private DBWorkerService dbWorkerService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        servletContextEvent.getServletContext().setAttribute("applicationContext", context);

        dbWorkerService = (DBWorkerService) context.getBean("db_worker");
        dbWorkerService.run();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        dbWorkerService.stop();
    }
}
