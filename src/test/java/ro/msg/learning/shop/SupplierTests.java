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
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.SupplierRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class SupplierTests {

    private static final String SUPPLIER_NAME = "Supplier Name";

    @Autowired
    SupplierRepository supplierRepository;

    @Test
    public void testReadSupplier() {

        Supplier supplier = supplierRepository.findSupplierById(100);

        Assert.assertNotNull(supplier);
        Assert.assertNotNull(supplier.getId());
        Assert.assertNotNull(supplier.getName());
        Assert.assertEquals("Supplier", supplier.getName());
    }

    @Test
    public void testCreateUpdateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName(SUPPLIER_NAME);

        Supplier created = supplierRepository.save(supplier);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(SUPPLIER_NAME, created.getName());

        created.setName(SUPPLIER_NAME + " changed");

        Supplier updated = supplierRepository.save(created);

        Assert.assertNotNull(updated);
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertTrue(updated.getName().contains("changed"));
    }

    @Test
    public void testDeleteSupplier() {

        supplierRepository.deleteSupplierById(100);

        Assert.assertEquals(1, supplierRepository.findAll().size());
    }

    @Test
    public void testReadAll() {
        Assert.assertNotNull(supplierRepository.save(new Supplier()));
        Assert.assertNotNull(supplierRepository.save(new Supplier()));
        Assert.assertNotNull(supplierRepository.save(new Supplier()));

        Assert.assertEquals(5, supplierRepository.findAll().size());
    }

}
