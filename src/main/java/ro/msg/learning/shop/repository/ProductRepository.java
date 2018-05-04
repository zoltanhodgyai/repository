package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Product;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public Product readProductById(Integer id) {

        return jdbcTemplate.queryForObject("select * from product where id = ?", new Object[] {id}, new ProductRowMapper());
    }




}
