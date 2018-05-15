package ro.msg.learning.shop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class CustomerTests extends ShopTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testReadCustomer() {

        List<Customer> customers = createCustomers();

        Assert.assertEquals(3, customers.size());

        Customer customer = customerService.findCustomerById(customers.get(0).getId());

        Assert.assertNotNull(customer);
        Assert.assertNotNull(customer.getId());
        Assert.assertNotNull(customer.getFirstName());
        Assert.assertEquals(CUSTOMER_LAST_NAME, customer.getLastName());
    }

    @Test
    public void testCreateUpdateCustomer() {
        Customer customer = getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME, CUSTOMER_PASSWORD);

        Customer created = customerService.save(customer);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(CUSTOMER_FIRST_NAME, created.getFirstName());

        created.setUsername("changedUsername");

        Customer updated = customerService.save(created);

        Assert.assertNotNull(updated);
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertTrue(updated.getUsername().contains("changed"));
    }

    @Test
    public void testDeleteCustomer() {
        List<Customer> customers = createCustomers();
        Assert.assertEquals(3, customers.size());

        customerService.deleteCustomerById(customers.get(2).getId());

        Assert.assertEquals(3, customerService.findAll().size());
    }

    @Test
    public void testReadAll() {
        createCustomers();

        Assert.assertEquals(4, customerService.findAll().size());
    }

    public void testCustomerPasswords() {
        createCustomers();

        Customer customer = customerService.findCustomerByUsername(CUSTOMER_USER_NAME);

        Assert.assertNotNull(customer);
        Assert.assertEquals(bCryptPasswordEncoder.encode(CUSTOMER_PASSWORD), customer.getPassword());
    }

    private List<Customer> createCustomers() {
        Customer customer1 = getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME, CUSTOMER_PASSWORD);
        Customer customer2 = getCustomer("Orsi", "Bocskai", "orsika", "orsikapass");
        Customer customer3 = getCustomer("Lacika", "Hodgyai", "lacika92", "lacikapass");

        // kell egy login service
        // s az utan, mindig vizsgalom a usert es azt hasznalom
        // s akkor tudom hasznalni a createOrdert addig, amig meg nem hivodik a log out
        // hmmmm???

        Customer c1 = customerService.save(customer1);
        Assert.assertNotNull(c1);
        Customer c2 = customerService.save(customer2);
        Assert.assertNotNull(c2);
        Customer c3 = customerService.save(customer3);
        Assert.assertNotNull(c3);

        List<Customer> result = new ArrayList<>();
        result.add(c1);
        result.add(c2);
        result.add(c3);

        return result;
    }

}
