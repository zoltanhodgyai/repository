package ro.msg.learning.shop.repository;

import org.springframework.jdbc.core.RowMapper;
import ro.msg.learning.shop.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRowMapper implements RowMapper<Supplier> {
    @Override
    public Supplier mapRow(ResultSet resultSet, int i) throws SQLException {

        Supplier supplier = new Supplier();
        supplier.setId(resultSet.getInt("ID"));
        supplier.setName(resultSet.getString("NAME"));

        return supplier;
    }
}
