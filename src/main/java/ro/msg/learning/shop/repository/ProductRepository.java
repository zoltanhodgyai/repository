package ro.msg.learning.shop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Product;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional(readOnly = true)
    public Product readProductById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("select * from product where id = ?", new Object[]{id},
                    new ProductRowMapper(productCategoryRepository, supplierRepository));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public Product createProduct(Product product) {
        if (product.getId() != null) {
            return updateProduct(product);
        }
        if (validateProduct(product)) {
            return null;
        }

        Integer newId = getLastId() != null ? getLastId() + 1 : 1;
        jdbcTemplate.update("insert into product(id, name, description, price, weight, product_category, supplier) " +
                "values (?, ?, ?, ?, ?, ?, ?)", newId, product.getName(), product.getDescription(), product.getPrice(),
                product.getWeight(), product.getCategory() == null ? null : product.getCategory().getId(), product.
                        getSupplier() == null ? null : product.getSupplier().getId());

        return readProductById(newId);
    }

    @Transactional
    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            return createProduct(product);
        }

        jdbcTemplate.update("update product set name = ?, description = ?, price = ?, weight = ?, " +
                        "product_category = ?, supplier = ? where id = ?",
                product.getName(), product.getDescription(), product.getPrice(),
                product.getWeight(), product.getCategory() == null ? null : product.getCategory().getId(),
                product.getSupplier() == null ? null : product.getSupplier().getId(), product.getId());

        return readProductById(product.getId());
    }

    @Transactional
    public void deleteProduct(Integer id) {

        jdbcTemplate.update("delete from product where id = ?", id);
    }

    @Transactional
    public Integer getLastId() {
        return jdbcTemplate.queryForObject("select max(id) from product", Integer.class);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        List<Product> result = jdbcTemplate.query("select id, name, description, price, weight, product_category, supplier " +
                "from product", new ProductRowMapper(productCategoryRepository, supplierRepository));

        return result;
    }

    private boolean validateProduct(Product product) {
        // todo @hodgyaiz: refine this later
        // todo now only the supplier and the product category are verified. They must be in the DB before creation
        if (product.getCategory() != null && product.getCategory().getId() != null && productCategoryRepository
                .readProductCategoryById(product.getCategory().getId()) == null) {
            return true;
        }

        if (product.getSupplier() != null && product.getSupplier().getId() != null && supplierRepository
                .readSupplierById(product.getSupplier().getId()) == null) {
            return true;
        }

        return false;
    }




}
