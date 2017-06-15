import db.DBService;
import db.DBServiceFactory;
import db.DBType;
import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBServiceTest {
    private static DBService dbServiceHibernate;
    private static DBService dbServiceMyBatis;

    @BeforeClass
    public static void prepare() {
        dbServiceHibernate = DBServiceFactory.build(DBType.HIBERNATE);
        dbServiceMyBatis = DBServiceFactory.build(DBType.MY_BATIS);

        UserDataSet oleg = new UserDataSet("Oleg");
        AddressDataSet addressDataSet = new AddressDataSet(4649, "Baker st.", oleg);
        oleg.setAddress(addressDataSet);
        PhoneDataSet phoneDataSet = new PhoneDataSet(495, "7778882", oleg);
        oleg.addPhone(phoneDataSet);
        dbServiceHibernate.save(oleg);

        UserDataSet victor = new UserDataSet("Victor");
        PhoneDataSet phoneDataSet1 = new PhoneDataSet(495, "6667771", victor);
        PhoneDataSet phoneDataSet2 = new PhoneDataSet(495, "9998885", victor);
        victor.addPhone(phoneDataSet1);
        victor.addPhone(phoneDataSet2);
        AddressDataSet addressDataSet1 = new AddressDataSet(1111, "Broadway.", victor);
        victor.setAddress(addressDataSet1);
        dbServiceHibernate.save(victor);

        UserDataSet olga = new UserDataSet("Olga");
        AddressDataSet addressDataSet2 = new AddressDataSet(4649, "Baker st.", olga);
        PhoneDataSet phoneDataSet3 = new PhoneDataSet(495, "1113334", olga);
        olga.addPhone(phoneDataSet3);
        olga.setAddress(addressDataSet2);
        dbServiceHibernate.save(olga);
    }

    @AfterClass
    public static void shutdown() {
        dbServiceHibernate.shutdown();
        dbServiceMyBatis.shutdown();
    }

    @Test
    public void readTest() {
        UserDataSet dataSet = dbServiceHibernate.read(2);

        assertEquals("Victor", dataSet.getName());

        dataSet = dbServiceMyBatis.read(2);

        assertEquals("Victor", dataSet.getName());
    }
}
