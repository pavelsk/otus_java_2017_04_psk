package db.dao;

import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDAO extends DAO {
    public UserDataSetDAO(Session session) {
        super(session);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);

        Root<UserDataSet> user = criteria.from(UserDataSet.class);

        criteria.where(builder.equal(user.get("name"), name));

        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

    public List<UserDataSet> readAllByAddress(int index, String street) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);

        Root<UserDataSet> user = criteria.from(UserDataSet.class);
        Join<UserDataSet, AddressDataSet> address = user.join("address");

        criteria.where(
                builder.equal(address.get("index"), index),
                builder.equal(address.get("street"), street)
        );

        return session.createQuery(criteria).list();
    }

    public UserDataSet readAllByPhone(int code, String number) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);

        Root<UserDataSet> user = criteria.from(UserDataSet.class);
        Join<UserDataSet, PhoneDataSet> phone = user.joinSet("phone");

        criteria.where(
                builder.equal(phone.get("code"), code),
                builder.equal(phone.get("number"), number)
        );

        return session.createQuery(criteria).uniqueResult();
    }
}
