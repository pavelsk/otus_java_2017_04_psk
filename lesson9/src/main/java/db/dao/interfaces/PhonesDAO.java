package db.dao.interfaces;

import models.PhoneDataSet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface PhonesDAO {
    @Insert("insert into phones (code, number, user_id) values (#{code}, #{number}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(PhoneDataSet dataSet);

    @Select("SELECT * FROM phones WHERE user_id = #{userId}")
    Set<PhoneDataSet> selectByUserId(long userId);
}
