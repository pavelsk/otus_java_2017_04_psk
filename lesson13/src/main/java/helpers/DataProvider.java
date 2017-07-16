package helpers;

import models.AddressDataSet;
import models.PhoneDataSet;
import models.UserDataSet;

import java.util.*;

public class DataProvider {
    private static List<String> names = Collections.synchronizedList(new ArrayList<>());

    private static final Random random = new Random();

    public static UserDataSet generateNewUser() {
        String name = UUID.randomUUID().toString();

        UserDataSet user = new UserDataSet(name);
        AddressDataSet addressDataSet = new AddressDataSet(1, "Street", user);
        user.setAddress(addressDataSet);
        PhoneDataSet phoneDataSet = new PhoneDataSet(1, "111", user);
        user.addPhone(phoneDataSet);

        names.add(name);

        return user;
    }

    public static String getRandomUserName() {
        if (names.size() == 0) {
            return null;
        }
        return names.get(random.nextInt(names.size()));
    }

    public static int getSize() {
        return names.size();
    }
}
