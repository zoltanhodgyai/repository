package ro.msg.learning.shop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.SecurityService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
public class SecurityServiceTests extends ShopTest{

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testLogin() {
        Customer customer = getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME, CUSTOMER_PASSWORD);

        Assert.assertNotNull(customerService.save(customer));

        securityService.login(CUSTOMER_USER_NAME, CUSTOMER_PASSWORD);

        Assert.assertEquals(CUSTOMER_USER_NAME, securityService.findLoggedInUsername());

        securityService.logout(CUSTOMER_USER_NAME);

        Assert.assertNull(securityService.findLoggedInUsername());
    }
}
