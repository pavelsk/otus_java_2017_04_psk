package db;

import app.CacheEngine;
import app.DBService;
import db.dao.DAO;
import db.dao.UserDataSetDAO;
import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    private CacheEngine<String, byte[]> cacheEngine;

    public DBServiceHibernateImpl(CacheEngine<String, byte[]> cacheEngine) {
        this.cacheEngine = cacheEngine;
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", DBService.LOGIN);
        configuration.setProperty("hibernate.connection.password", DBService.PASS);
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public boolean isOpen() {
        return sessionFactory.isOpen();
    }

    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            DAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
        }
    }

    public UserDataSet read(long id) {
        String cacheKey = "userId-" + String.valueOf(id);
        byte[] cacheValue = cacheEngine.get(cacheKey);

        if (cacheValue != null) {
            return (UserDataSet) SerializationUtils.deserialize(cacheValue);
        }

        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet userDataSet = dao.read(id);

            cacheEngine.put(cacheKey, SerializationUtils.serialize(userDataSet));

            return userDataSet;
        });
    }

    public UserDataSet readByName(String name) {
        String cacheKey = "userName-" + name;
        byte[] cacheValue = cacheEngine.get(cacheKey);

        if (cacheValue != null) {
            return (UserDataSet) SerializationUtils.deserialize(cacheValue);
        }

        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet userDataSet = dao.readByName(name);

            cacheEngine.put(cacheKey, SerializationUtils.serialize(userDataSet));

            return userDataSet;
        });
    }

    @Override
    public UserDataSet readByPhone(int code, String number) {
        String cacheKey = "userPhone-" + String.valueOf(code) + "-" + number;
        byte[] cacheValue = cacheEngine.get(cacheKey);

        if (cacheValue != null) {
            return (UserDataSet) SerializationUtils.deserialize(cacheValue);
        }

        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet userDataSet = dao.readAllByPhone(code, number);

            cacheEngine.put(cacheKey, SerializationUtils.serialize(userDataSet));

            return userDataSet;
        });
    }

    @Override
    public List<UserDataSet> readAllByAddress(int index, String street) {
        String cacheKey = "usersAddresses-" + String.valueOf(index) + "-" + street;
        byte[] cacheValue = cacheEngine.get(cacheKey);

        if (cacheValue != null) {
            return (List<UserDataSet>) SerializationUtils.deserialize(cacheValue);
        }

        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            List<UserDataSet> userDataSets = dao.readAllByAddress(index, street);

            cacheEngine.put(cacheKey, SerializationUtils.serialize((Serializable) userDataSets));

            return userDataSets;
        });
    }

    public List<UserDataSet> readAll() {
        String cacheKey = "usersAll";
        byte[] cacheValue = cacheEngine.get(cacheKey);

        if (cacheValue != null) {
            return (List<UserDataSet>) SerializationUtils.deserialize(cacheValue);
        }
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            List<UserDataSet> userDataSets = dao.readAll();

            cacheEngine.put(cacheKey, SerializationUtils.serialize((Serializable) userDataSets));

            return userDataSets;
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
