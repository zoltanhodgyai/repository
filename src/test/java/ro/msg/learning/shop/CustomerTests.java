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
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class CustomerTests extends ShopTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testReadCustomer() {

        List<Customer> customers = createCustomers();

        Assert.assertEquals(3, customers.size());

        Customer customer = customerRepository.findCustomerById(customers.get(0).getId());

        Assert.assertNotNull(customer);
        Assert.assertNotNull(customer.getId());
        Assert.assertNotNull(customer.getFirstName());
        Assert.assertEquals(CUSTOMER_LAST_NAME, customer.getLastName());
    }

    @Test
    public void testCreateUpdateCustomer() {
        Customer customer = getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME);

        Customer created = customerRepository.save(customer);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(CUSTOMER_FIRST_NAME, created.getFirstName());

        created.setUsername("changedUsername");

        Customer updated = customerRepository.save(created);

        Assert.assertNotNull(updated);
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertTrue(updated.getUsername().contains("changed"));
    }

    @Test
    public void testDeleteCustomer() {
        List<Customer> customers = createCustomers();
        Assert.assertEquals(3, customers.size());

        customerRepository.deleteCustomerById(customers.get(2).getId());

        Assert.assertEquals(3, customerRepository.findAll().size());
    }

    @Test
    public void testReadAll() {
        createCustomers();

        Assert.assertEquals(4, customerRepository.findAll().size());
    }

    private List<Customer> createCustomers() {
        Customer customer1 = getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME);
        Customer customer2 = getCustomer("Orsi", "Bocskai", "orsika");
        Customer customer3 = getCustomer("Lacika", "Hodgyai", "lacika92");

        Customer c1 = customerRepository.save(customer1);
        Assert.assertNotNull(c1);
        Customer c2 = customerRepository.save(customer2);
        Assert.assertNotNull(c2);
        Customer c3 = customerRepository.save(customer3);
        Assert.assertNotNull(c3);

        List<Customer> result = new ArrayList<>();
        result.add(c1);
        result.add(c2);
        result.add(c3);

        return result;
    }

}
