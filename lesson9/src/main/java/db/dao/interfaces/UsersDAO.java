package db.dao.interfaces;

import db.handlers.AddressDataSetHandler;
import models.AddressDataSet;
import models.UserDataSet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

/**
 * Mapper for table users.
 */
public interface UsersDAO {
    @Insert("insert into users (name) values (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(UserDataSet dataSet);

    //left join addresses on users.id=addresses.user_id WHERE name = #{name} limit 1
    @Select("SELECT * FROM users WHERE id = #{id} limit 1")
    UserDataSet select(long id);

    @Select("SELECT u.id, u.name, a.id as address_id, a.street, a.index FROM users u left join addresses a on u.id=a" +
            ".user_id WHERE name = #{name} limit 1")
    @Results(value = {
            @Result(column = "name", property = "name"),
            @Result(
                    property = "address",
                    column = "id",
                    javaType = AddressDataSet.class,
                    one = @One(fetchType = FetchType.EAGER),
                    typeHandler = AddressDataSetHandler.class
            ),
            @Result(
                    property = "phone",
                    column = "id",
                    javaType = Set.class,
                    many = @Many(select = "db.dao.interfaces.PhonesDAO.selectByUserId")
            )
    })
    UserDataSet selectByName(String name);

    @Select("SELECT * FROM users")
    List<UserDataSet> selectAll();

    @Results(value = {
            @Result(column = "name", property = "name"),
            @Result(
                    property = "address",
                    column = "id",
                    javaType = AddressDataSet.class,
                    one = @One(select = "db.dao.interfaces.AddressesDAO.selectByUserId")
            ),
            @Result(
                    property = "phone",
                    column = "id",
                    javaType = Set.class,
                    many = @Many(select = "db.dao.interfaces.PhonesDAO.selectByUserId")
            )
    })
    @Select("SELECT * FROM users u left join phones p on u.id=p.user_id " +
            "WHERE p.code = #{code} and p.number = #{number} limit 1")
    UserDataSet selectByPhone(@Param("code") int code, @Param("number") String number);


    @Select("SELECT u.id, u.name, a.id as address_id, a.street, a.index FROM users u left join addresses a on u.id=a" +
            ".user_id WHERE a.index = #{index} and a.street = #{street}")
    @Results(value = {
            @Result(column = "name", property = "name"),
            @Result(
                    property = "address",
                    column = "id",
                    javaType = AddressDataSet.class,
                    one = @One(fetchType = FetchType.EAGER),
                    typeHandler = AddressDataSetHandler.class
            ),
            @Result(
                    property = "phone",
                    column = "id",
                    javaType = Set.class,
                    many = @Many(select = "db.dao.interfaces.PhonesDAO.selectByUserId")
            )
    })
    List<UserDataSet> selectByAddress(@Param("index") int index, @Param("street") String street);
}
