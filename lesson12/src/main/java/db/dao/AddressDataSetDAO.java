package db.dao;

import models.AddressDataSet;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AddressDataSetDAO extends DAO {
    public AddressDataSetDAO(Session session) {
        super(session);
    }

    public AddressDataSet read(long id) {
        return session.load(AddressDataSet.class, id);
    }

    public List<AddressDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AddressDataSet> criteria = builder.createQuery(AddressDataSet.class);
        criteria.from(AddressDataSet.class);
        return session.createQuery(criteria).list();
    }
}
