package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Product;

import java.util.List;

public interface ProductRepository extends Repository<Product, Integer> {

    @Transactional
    Product save(Product product);

    @Transactional(readOnly = true)
    Product findProductById(Integer id);

    @Transactional(readOnly = true)
    List<Product> findAll();

    @Transactional
    void deleteProductById(Integer id);
}
