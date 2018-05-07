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
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class ProductTests {

    private static final String PRODUCT_NAME = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";

    @Autowired
	private ProductRepository productRepository;

    @Autowired
	private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

	@Test
	public void testCreateProduct() {

	    ProductCategory productCategory = createProductCategory();
	    Supplier supplier = readSupplier();

	    Product product = createProduct(PRODUCT_NAME, PRODUCT_DESCRIPTION, BigDecimal.valueOf(12), 1d, productCategory, supplier);

	    Product createdProduct = productRepository.createProduct(product);

	    Assert.assertNotNull(createdProduct);
	    Assert.assertNotNull(createdProduct.getId());
	    Assert.assertEquals(Double.valueOf(1), createdProduct.getWeight());
	    Assert.assertEquals(Integer.valueOf(1), createdProduct.getCategory().getId());

	    product = productRepository.createProduct(createProduct(PRODUCT_NAME, PRODUCT_DESCRIPTION, BigDecimal.valueOf(20), 2d, productCategory, supplier));

	    Assert.assertNotNull(product);
	    Assert.assertNotNull(product.getId());
	    Assert.assertEquals(1, product.getId() - (long)createdProduct.getId());
	}

	@Test
	public void testReadProduct() {

	    createProducts();

	    Product product = productRepository.readProductById(2);

        Assert.assertNotNull(product);
        Assert.assertNotNull(product.getId());
	    Assert.assertEquals(BigDecimal.valueOf(16.00).setScale(2), product.getPrice());
	    Assert.assertEquals(Double.valueOf(6), product.getWeight());
	}

	@Test
	public void testUpdateProduct() {

	    createProducts();

	    Product product = productRepository.readProductById(2);

        Assert.assertEquals(Double.valueOf(6), product.getWeight());

        product.setWeight(9d);
        product.setName("Changed");
        product.setPrice(BigDecimal.valueOf(32));

        Product updated = productRepository.updateProduct(product);

        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getId());
        Assert.assertEquals(product.getId(), updated.getId());
        Assert.assertEquals(Double.valueOf(9), updated.getWeight());
	}

    @Test
    public void testUpdateWrong() {
	    Product product = productRepository.updateProduct(createProduct(PRODUCT_NAME, PRODUCT_DESCRIPTION,
                BigDecimal.valueOf(1), 1d, createProductCategory(), readSupplier()));

	    Assert.assertNotNull(product);
	    Assert.assertNotNull(product.getId());
    }

    @Test
    public void testCreateWrong() {
	    createProducts();

	    Product product = createProduct(PRODUCT_NAME, PRODUCT_DESCRIPTION,
                BigDecimal.valueOf(1), 1d, createProductCategory(), readSupplier());
	    product.setId(2);

	    Product p = productRepository.createProduct(product);

	    Assert.assertNotNull(p);
	    Assert.assertNotNull(p.getId());
	    Assert.assertEquals(Integer.valueOf(2), p.getId());
	    Assert.assertEquals(Double.valueOf(1), p.getWeight());
    }

	@Test
	public void testDeleteProduct() {

	    createProducts();

	    productRepository.deleteProduct(2);

	    Assert.assertEquals(2, productRepository.findAll().size());
        Assert.assertNull(productRepository.readProductById(2));
	}



	@Test
	public void testFindAllProducts() {
	    createProducts();

        List<Product> products = productRepository.findAll();

        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
	}

	private void createProducts() {

        ProductCategory productCategory = createProductCategory();
        Supplier supplier = readSupplier();

        Product p1 = createProduct(PRODUCT_NAME+1, PRODUCT_DESCRIPTION+1, BigDecimal.valueOf(12), 1d, productCategory, supplier);
        Product p2 = createProduct(PRODUCT_NAME+2, PRODUCT_DESCRIPTION+2, BigDecimal.valueOf(16), 6d, productCategory, supplier);
        Product p3 = createProduct(PRODUCT_NAME+3, PRODUCT_DESCRIPTION+3, BigDecimal.valueOf(22), 9d, productCategory, supplier);

        Assert.assertNotNull(productRepository.createProduct(p1));
        Assert.assertNotNull(productRepository.createProduct(p2));
        Assert.assertNotNull(productRepository.createProduct(p3));
    }

    private Product createProduct(String name, String description, BigDecimal price, Double weight, ProductCategory productCategory, Supplier supplier) {
	    Product product = new Product();
	    product.setName(name);
	    product.setDescription(description);
	    product.setPrice(price);
	    product.setWeight(weight);
	    product.setCategory(productCategory);
	    product.setSupplier(supplier);

	    return product;
    }

    private Supplier readSupplier() {
	    return supplierRepository.readSupplierById(1);
    }

    private ProductCategory createProductCategory() {
	    ProductCategory productCategory = new ProductCategory();
	    productCategory.setName("Name");
	    productCategory.setDescription("Description");

	    return productCategoryRepository.createProductCategory(productCategory);
    }
}
