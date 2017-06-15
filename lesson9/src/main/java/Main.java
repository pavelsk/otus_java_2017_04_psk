import db.DBService;
import db.DBServiceFactory;
import db.DBType;
import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        DBService dbService = DBServiceFactory.build(DBType.HIBERNATE);
        DBService dbService = DBServiceFactory.build(DBType.MY_BATIS);
        System.out.println(dbService.getLocalStatus());

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

        dataSet = dbService.readByName("Victor");
        System.out.println(dataSet);
        System.out.println();

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
    }
}
