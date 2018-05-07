package ro.msg.learning.shop.repository;

import org.springframework.jdbc.core.RowMapper;
import ro.msg.learning.shop.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

    @Override
    public ProductCategory mapRow(ResultSet resultSet, int i) throws SQLException {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(resultSet.getInt("ID"));
        productCategory.setName(resultSet.getString("NAME"));
        productCategory.setDescription(resultSet.getString("DESCRIPTION"));

        return productCategory;
    }
}
