package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository extends Repository<ProductCategory, Integer> {

    @Transactional(readOnly = true)
    ProductCategory findProductCategoryById(Integer id);

    @Transactional
    ProductCategory save(ProductCategory productCategory);

    @Transactional(readOnly = true)
    List<ProductCategory> findAll();

    @Transactional
    void deleteProductCategoryById(Integer id);
}
