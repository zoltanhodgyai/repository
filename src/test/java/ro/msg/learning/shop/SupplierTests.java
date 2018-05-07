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
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class SupplierTests {

    private static final String NAME_TEXT = "Supplier";

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
	public void testReadSupplier() {

    	Supplier supplier = supplierRepository.readSupplierById(1);

    	Assert.assertNotNull(supplier);
    	Assert.assertNotNull(supplier.getId());
    	Assert.assertEquals(Integer.valueOf(1), supplier.getId());
	}

}
