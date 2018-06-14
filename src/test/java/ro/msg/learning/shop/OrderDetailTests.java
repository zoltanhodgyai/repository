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
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class OrderDetailTests extends ShopTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testOrderDetailFull() {
        Order order = orderRepository.findOrderById(100);
        Assert.assertNotNull(order);
        Product product = productRepository.findProductById(100);
        Assert.assertNotNull(product);

        OrderDetail orderDetail = new OrderDetail(null, order, product, 12);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(12);

        OrderDetail created = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(created);

        OrderDetail read = orderDetailRepository.findOrderDetailByOrderAndProduct(orderDetail.getOrder(), orderDetail.getProduct());
        Assert.assertNotNull(read);
        Assert.assertEquals(orderDetail.getQuantity(), read.getQuantity());
        Assert.assertEquals(1, orderDetailRepository.findAll().size());

        orderDetailRepository.deleteOrderDetailByOrderAndProduct(orderDetail.getOrder(), orderDetail.getProduct());
        Assert.assertEquals(0, orderDetailRepository.findAll().size());
    }


}
