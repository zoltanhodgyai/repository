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
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.repository.AddressRepository;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class OrderTests extends ShopTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testOrderFull() {
        Address address = addressRepository.save(getAddress());
        Assert.assertNotNull(address);

        Customer customer = customerRepository.save(getCustomer(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, CUSTOMER_USER_NAME, CUSTOMER_PASSWORD));
        Assert.assertNotNull(customer);

        Location location = new Location();
        location.setName("Location");
        Address locationAddress = addressRepository.save(getAddress(COUNTRY, CITY, COUNTY, "Plopilor 18."));
        Assert.assertNotNull(locationAddress);
        location.setAddress(locationAddress);
        location = locationRepository.save(location);
        Assert.assertNotNull(location);

        Order order = new Order();
        order.setShippedFrom(location);
        order.setCustomer(customer);
        order.setAddress(address);
        order.setOrderDateTime(LocalDateTime.now());

        Order created = orderRepository.save(order);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(address.getId(), created.getAddress().getId());
        Assert.assertEquals(customer.getId(), created.getCustomer().getId());
        Assert.assertEquals(location.getId(), created.getShippedFrom().getId());
        Assert.assertEquals(location.getAddress().getId(), created.getShippedFrom().getAddress().getId());

        Order read = orderRepository.findOrderById(created.getId());
        Assert.assertNotNull(read);
        Assert.assertEquals(created.getId(), read.getId());
        Assert.assertEquals(2, orderRepository.findAll().size());

        Order secondOrder = read;
        secondOrder.setOrderDateTime(read.getOrderDateTime().minusYears(1));
        secondOrder.setId(null);

        Assert.assertNotNull(orderRepository.save(secondOrder));

        List<Order> orders = orderRepository.findAllOrOrderByOrderDateTimeIsBefore(LocalDateTime.now());
        Assert.assertEquals(3, orders.size());
        Assert.assertEquals(1, orderRepository.findAllOrOrderByOrderDateTimeIsBefore(LocalDateTime.now().minusYears(2)).size());


        orderRepository.deleteOrderById(read.getId());
        Assert.assertEquals(2, orderRepository.findAll().size());
    }
}
