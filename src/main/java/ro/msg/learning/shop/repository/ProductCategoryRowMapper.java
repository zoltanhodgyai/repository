package ro.msg.learning.shop.repository;

import org.springframework.jdbc.core.RowMapper;
import ro.msg.learning.shop.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

    @Override
    public ProductCategory mapRow(ResultSet resultSet, int i) throws SQLException {

        ProductCategory productCategory = new ProductCategory();
        // todo @hodgyaiz: change this later (from Long to Integer in ProductCategory)
        productCategory.setId(resultSet.getLong("ID"));
        productCategory.setName(resultSet.getString("NAME"));
        productCategory.setDescription(resultSet.getString("DESCRIPTION"));

        return productCategory;
    }
}
