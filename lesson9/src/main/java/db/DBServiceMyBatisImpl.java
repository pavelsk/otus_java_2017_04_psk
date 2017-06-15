package db;

import db.dao.interfaces.AddressesDAO;
import db.dao.interfaces.DAO;
import db.dao.interfaces.PhonesDAO;
import db.dao.interfaces.UsersDAO;
import models.UserDataSet;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.List;

public class DBServiceMyBatisImpl implements DBService {
    private SqlSessionFactory sqlSessionFactory;

    public DBServiceMyBatisImpl() {
        sqlSessionFactory = getSqlSessionFactory(getMySQLDataSource());
        createTableIfNotExists();
    }

    @Override
    public String getLocalStatus() {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            return session.toString();
        }
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO usersDAO = session.getMapper(UsersDAO.class);
            PhonesDAO phonesDAO = session.getMapper(PhonesDAO.class);
            AddressesDAO addressesDAO = session.getMapper(AddressesDAO.class);

            usersDAO.save(dataSet);

            dataSet.getPhone().forEach(phoneDataSet -> {
                phoneDataSet.setUser(dataSet);
                phonesDAO.save(phoneDataSet);
            });

            dataSet.getAddress().setUser(dataSet);
            addressesDAO.save(dataSet.getAddress());

            session.commit();
        }
    }

    @Override
    public UserDataSet read(long id) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO dao = session.getMapper(UsersDAO.class);
            return dao.select(id);
        }
    }

    @Override
    public UserDataSet readByName(String name) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO dao = session.getMapper(UsersDAO.class);
            return dao.selectByName(name);
        }
    }

    @Override
    public UserDataSet readByPhone(int code, String number) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO dao = session.getMapper(UsersDAO.class);
            return dao.selectByPhone(code, number);
        }
    }

    @Override
    public List<UserDataSet> readAllByAddress(int index, String street) {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO dao = session.getMapper(UsersDAO.class);
            return dao.selectByAddress(index, street);
        }
    }

    @Override
    public List<UserDataSet> readAll() {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            UsersDAO dao = session.getMapper(UsersDAO.class);
            return dao.selectAll();
        }
    }

    @Override
    public void shutdown() {}

    private SqlSessionFactory getSqlSessionFactory(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        LogFactory.useStdOutLogging();

        configuration.addMapper(DAO.class);
        configuration.addMapper(UsersDAO.class);
        configuration.addMapper(PhonesDAO.class);
        configuration.addMapper(AddressesDAO.class);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private DataSource getMySQLDataSource() {
        PooledDataSource ds = new PooledDataSource();

        ds.setDriver("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/db_example");
        ds.setUsername(DBService.LOGIN);
        ds.setPassword(DBService.PASS);

        return ds;
    }

    private void createTableIfNotExists() {
        try (SqlSession session = sqlSessionFactory.openSession(false)) {
            DAO dao = session.getMapper(DAO.class);

            dao.createUsers();
            dao.createPhones();
            dao.createAddresses();

            session.commit();
        }
    }
}
