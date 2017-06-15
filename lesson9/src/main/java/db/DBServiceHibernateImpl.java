package db;

import db.dao.AddressDataSetDAO;
import db.dao.DAO;
import db.dao.PhoneDataSetDAO;
import db.dao.UserDataSetDAO;
import models.AddressDataSet;
import models.DataSet;
import models.PhoneDataSet;
import models.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", DBService.LOGIN);
        configuration.setProperty("hibernate.connection.password", DBService.PASS);
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            DAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
        }
    }

    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    @Override
    public UserDataSet readByPhone(int code, String number) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAllByPhone(code, number);
        });
    }

    @Override
    public List<UserDataSet> readAllByAddress(int index, String street) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAllByAddress(index, street);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
