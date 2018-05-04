package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Supplier;

@Repository
public class SupplierRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public Supplier readSupplierById(Integer id) {

        return jdbcTemplate.queryForObject("select * from supplier where id = ?", new Object[] {id}, new SupplierRowMapper());
    }
}
