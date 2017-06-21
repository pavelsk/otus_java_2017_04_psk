package db;


import models.UserDataSet;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface DBService {
    String LOGIN = "pavel";
    String PASS = "pavel";

    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    UserDataSet readByPhone(int code, String number);

    List<UserDataSet> readAllByAddress(int index, String street);

    List<UserDataSet> readAll();

    void shutdown();
}
