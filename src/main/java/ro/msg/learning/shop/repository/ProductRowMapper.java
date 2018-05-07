package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
class ProductRowMapper implements RowMapper<Product> {

    ProductCategoryRepository productCategoryRepository;

    SupplierRepository supplierRepository;

    public ProductRowMapper(ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("ID"));
        product.setName(resultSet.getString("NAME"));
        product.setDescription(resultSet.getString("DESCRIPTION"));
        product.setPrice(resultSet.getBigDecimal("PRICE"));
        product.setWeight(resultSet.getDouble("WEIGHT"));
        product.setCategory(productCategoryRepository.readProductCategoryById(resultSet.getInt("PRODUCT_CATEGORY")));
        product.setSupplier(supplierRepository.readSupplierById(resultSet.getInt("SUPPLIER")));

        return product;
    }
}
