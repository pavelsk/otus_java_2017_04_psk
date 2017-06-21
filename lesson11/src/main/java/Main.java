import cache.CacheEngine;
import cache.CacheEngineImpl;
import db.DBService;
import db.DBServiceHibernateImpl;
import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CacheEngine<String, byte[]> cacheEngine = new CacheEngineImpl<>(10, 0, 0);
        DBService dbService = new DBServiceHibernateImpl(cacheEngine);

        doPrepareWork(dbService);
        doSelectWork(dbService);

        dbService.shutdown();
    }

    private static void doPrepareWork(DBService dbService) {
        UserDataSet oleg = new UserDataSet("Oleg");
        AddressDataSet addressDataSet = new AddressDataSet(4649, "Baker st.", oleg);
        oleg.setAddress(addressDataSet);
        PhoneDataSet phoneDataSet = new PhoneDataSet(495, "7778882", oleg);
        oleg.addPhone(phoneDataSet);
        dbService.save(oleg);

        UserDataSet victor = new UserDataSet("Victor");
        PhoneDataSet phoneDataSet1 = new PhoneDataSet(495, "6667771", victor);
        PhoneDataSet phoneDataSet2 = new PhoneDataSet(495, "9998885", victor);
        victor.addPhone(phoneDataSet1);
        victor.addPhone(phoneDataSet2);
        AddressDataSet addressDataSet1 = new AddressDataSet(1111, "Broadway.", victor);
        victor.setAddress(addressDataSet1);
        dbService.save(victor);

        UserDataSet olga = new UserDataSet("Olga");
        AddressDataSet addressDataSet2 = new AddressDataSet(4649, "Baker st.", olga);
        PhoneDataSet phoneDataSet3 = new PhoneDataSet(495, "1113334", olga);
        olga.addPhone(phoneDataSet3);
        olga.setAddress(addressDataSet2);
        dbService.save(olga);
    }

    private static void doSelectWork(DBService dbService) {
        UserDataSet dataSet = dbService.read(2);
        System.out.println(dataSet);
        System.out.println();

        dataSet = dbService.readByName("sully");
        System.out.println(dataSet);
        System.out.println();

        UserDataSet victorDataSet = dbService.readByName("Victor");
        System.out.println(victorDataSet);
        System.out.println();
        victorDataSet.setName("Some other name");

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }
        System.out.println();

        dataSets = dbService.readAllByAddress(4649, "Baker st.");
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }
        System.out.println();

        dataSet = dbService.readByPhone(495, "9998885");
        System.out.println(dataSet);
        System.out.println();

        dataSet = dbService.readByName("Victor");
        System.out.println(victorDataSet);
        System.out.println(dataSet);
        System.out.println();

        System.out.println(dbService.readByName("Victor11"));
    }
}
