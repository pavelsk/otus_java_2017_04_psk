package db;

import static db.DBType.HIBERNATE;

public class DBServiceFactory {
    public static DBService build(DBType type) {
        switch (type) {
            case HIBERNATE:
                return new DBServiceHibernateImpl();
            case MY_BATIS:
                return new DBServiceMyBatisImpl();
        }

        return null;
    }
}
