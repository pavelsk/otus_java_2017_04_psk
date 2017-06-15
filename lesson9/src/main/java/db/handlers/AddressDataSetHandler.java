package db.handlers;

import models.AddressDataSet;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDataSetHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        AddressDataSet addressDataSet = new AddressDataSet(
                rs.getInt("index"), rs.getString("street")
        );
        addressDataSet.setId(rs.getInt("address_id"));

        return addressDataSet;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
