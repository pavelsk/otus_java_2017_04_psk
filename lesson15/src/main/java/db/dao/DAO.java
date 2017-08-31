package db.dao;

import models.DataSet;
import org.hibernate.Session;

public class DAO {
    protected Session session;

    public DAO(Session session) {
        this.session = session;
    }

    public void save(DataSet dataSet) {
        session.save(dataSet);
    }
}
