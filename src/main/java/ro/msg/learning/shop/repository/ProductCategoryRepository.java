package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.ProductCategory;

public class ProductCategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public ProductCategory readProductCategoryById(Integer id) {
        return jdbcTemplate.queryForObject("select * from productCategory where id = ?", new Object[] {id},  new ProductCategoryRowMapper());
    }
}
