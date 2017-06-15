package db.dao.interfaces;

import models.AddressDataSet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface AddressesDAO {
    @Insert("insert into addresses (`index`, street, user_id) values (#{index}, #{street}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(AddressDataSet dataSet);

    @Select("SELECT * FROM addresses WHERE user_id = #{userId} limit 1")
    AddressDataSet selectByUserId(long userId);
}
