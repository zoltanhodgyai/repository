package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductCategoryDataStore {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDataStore(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    Map<String, Object> getProductCategory(final Integer id) {
        ProductCategory productCategory = productCategoryRepository.findProductCategoryById(id);

        return createProductCategory(productCategory);
    }

    List<Map<String, Object>> getProductCategories() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        for (ProductCategory productCategory : productCategories) {
            result.add(createProductCategory(productCategory));
        }
        return result;
    }

    Map<String, Object> saveProductCategory(final ProductCategory productCategory) {
        ProductCategory result = productCategoryRepository.save(productCategory);

        return createProductCategory(result);
    }

    void deleteProductCategory(final Integer id) throws ODataException {
        if (productCategoryRepository.findProductCategoryById(id) != null) {
            productCategoryRepository.deleteProductCategoryById(id);
        } else {
            throw new ODataException("No Product Category found for id: " + id);
        }
    }

    private Map<String, Object> createProductCategory(ProductCategory productCategory) {
        Map<String, Object> data = new HashMap<>();

        data.put("Id", productCategory.getId());
        data.put("Name", productCategory.getName());
        data.put("Description", productCategory.getDescription());

        return data;
    }
}
