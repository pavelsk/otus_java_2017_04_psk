package db.dao.interfaces;

import db.builders.SchemaBuilder;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface DAO {

    /**
     * Black magic for execution of custom query.
     */
    String SQL = "sql";

    @Update("${" + SQL + "}")
    void execute(Map<String, String> m);

    @SelectProvider(type = SchemaBuilder.class, method = "buildUsers")
    void createUsers();

    @SelectProvider(type = SchemaBuilder.class, method = "buildPhones")
    void createPhones();

    @SelectProvider(type = SchemaBuilder.class, method = "buildAddresses")
    void createAddresses();
}
