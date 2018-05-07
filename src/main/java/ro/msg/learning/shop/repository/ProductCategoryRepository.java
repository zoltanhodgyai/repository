package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.ProductCategory;

import java.util.List;

@Repository
public class ProductCategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public ProductCategory readProductCategoryById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("select * from product_category where id = ?", new Object[]{id},
                    new ProductCategoryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public ProductCategory createProductCategory(ProductCategory productCategory) {

        if (productCategory.getId() != null) {
            return updateProductCategory(productCategory);
        }
        Integer newId = getLastId() != null ? getLastId() + 1 : 1;
        jdbcTemplate.update("insert into product_category(id, name, description) values (?, ?, ?)", newId, productCategory.getName(), productCategory.getDescription());

        return readProductCategoryById(newId);
    }

    @Transactional
    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        if (productCategory.getId() == null) {
            return createProductCategory(productCategory);
        }
        jdbcTemplate.update("update product_category set name = ?, description = ? where id = ?", productCategory.
                getName(), productCategory.getDescription(), productCategory.getId());

        return readProductCategoryById(productCategory.getId());
    }

    @Transactional
    public void deleteProductCategory(Integer id) {
        jdbcTemplate.update("delete from product_category where id = ?", id);
    }

    @Transactional
    public Integer getLastId() {
        return jdbcTemplate.queryForObject("select max(id) from product_category", Integer.class);
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> findAll() {
        List<ProductCategory> result = jdbcTemplate.query("select id, name, description from product_category", new ProductCategoryRowMapper());

        return result;
    }
}
