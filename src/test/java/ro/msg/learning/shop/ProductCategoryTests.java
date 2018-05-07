package ro.msg.learning.shop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class ProductCategoryTests {

    private static final String DESCRIPTION_TEXT = "Description";
    private static final String DESCRIPTION_TEXT_SHORT = "Desc";
    private static final String NAME_TEXT = "Product Category";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

	@Test
	public void testCreateProductCategory() {

	    ProductCategory productCategory = createProductCategory("Product Category 1", DESCRIPTION_TEXT);

	    ProductCategory created = productCategoryRepository.createProductCategory(productCategory);

	    Assert.assertNotNull(created);
	    Assert.assertNotNull(created.getId());
	    Assert.assertEquals(Integer.valueOf(1), created.getId());
        Assert.assertTrue(created.getName().contains("1"));

	    ProductCategory pc2 = createProductCategory("PC 2", "Desc 2");

	    pc2 = productCategoryRepository.createProductCategory(pc2);

	    Assert.assertNotNull(pc2);
	    Assert.assertNotNull(pc2.getId());
	    Assert.assertEquals(1, pc2.getId() - (long)created.getId());
	}

	@Test
	public void testReadProductCategory() {

	    Assert.assertEquals(3, createProductCategories().size());

	    ProductCategory productCategory = productCategoryRepository.readProductCategoryById(2);

	    Assert.assertNotNull(productCategory);
	    Assert.assertEquals("Name 2", productCategory.getName());

        ProductCategory fail = productCategoryRepository.readProductCategoryById(4);

        Assert.assertNull(fail);
	}

	@Test
	public void testUpdateProductCategory() {

	    ProductCategory productCategory = createProductCategory("Prod Cat 1", DESCRIPTION_TEXT_SHORT);

	    productCategory = productCategoryRepository.createProductCategory(productCategory);

	    Assert.assertNotNull(productCategory);
	    Assert.assertNotNull(productCategory.getId());
	    Assert.assertEquals(DESCRIPTION_TEXT_SHORT, productCategory.getDescription());

	    productCategory.setName(NAME_TEXT);
	    productCategory.setDescription(DESCRIPTION_TEXT);

	    ProductCategory updatedProductCategory = productCategoryRepository.updateProductCategory(productCategory);

	    Assert.assertNotNull(updatedProductCategory);
	    Assert.assertNotNull(updatedProductCategory.getId());
	    Assert.assertEquals(productCategory.getId(), updatedProductCategory.getId());
	    Assert.assertEquals(NAME_TEXT, updatedProductCategory.getName());
	    Assert.assertEquals(DESCRIPTION_TEXT, updatedProductCategory.getDescription());
	}

	@Test
	public void testDeleteProductCategory() {

	    createProductCategories();

	    productCategoryRepository.deleteProductCategory(2);

	    Assert.assertEquals(2, productCategoryRepository.findAll().size());
	    Assert.assertNull(productCategoryRepository.readProductCategoryById(2));
	}

	@Test
	public void testProductCategoryAll() {

	    createProductCategories();

        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        Assert.assertEquals(3, productCategories.size());
	}

    private List<ProductCategory> createProductCategories() {

        Assert.assertNotNull(productCategoryRepository.createProductCategory(createProductCategory("Name 1", "Desc 1")));
        Assert.assertNotNull(productCategoryRepository.createProductCategory(createProductCategory("Name 2", "Desc 2")));
        Assert.assertNotNull(productCategoryRepository.createProductCategory(createProductCategory("Name 3", "Desc 3")));

        return productCategoryRepository.findAll();
    }

	private ProductCategory createProductCategory(String name, String description) {
	    ProductCategory productCategory = new ProductCategory();
	    productCategory.setName(name);
	    productCategory.setDescription(description);

	    return productCategory;
    }

}
