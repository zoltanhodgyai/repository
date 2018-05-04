package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import ro.msg.learning.shop.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

class ProductRowMapper implements RowMapper<Product> {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        // todo @hodgyaiz: change this later (from Long to Integer in Product)
        product.setId(resultSet.getInt("ID"));
        product.setName(resultSet.getString("NAME"));
        product.setDescription(resultSet.getString("DESCRIPTION"));
        product.setPrice(resultSet.getBigDecimal("PRICE"));
        product.setWeight(resultSet.getDouble("WEIGHT"));
        product.setCategory(productCategoryRepository.readProductCategoryById(resultSet.getInt("CATEGORY")));
        product.setSupplier(supplierRepository.readSupplierById(resultSet.getInt("SUPPLIER")));

        return product;
    }
}
