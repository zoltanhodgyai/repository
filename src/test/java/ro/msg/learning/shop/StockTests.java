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
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.AddressRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.SecurityService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.util.Strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class StockTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Strategy strategy;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SecurityService securityService;

    @Test
    public void testStockFull() {
        Location location = locationRepository.findLocationById(100);
        Assert.assertNotNull(location);
        Product product = productRepository.findProductById(100);
        Assert.assertNotNull(product);

        Stock stock = new Stock();
        StockKey stockKey = new StockKey();
        stockKey.setProduct(product);
        stockKey.setLocation(location);
        stock.setStockKey(stockKey);
        stock.setQuantity(12);

        Stock created = stockRepository.save(stock);
        Assert.assertNotNull(created);

        Stock read = stockRepository.findStockByStockKey(stock.getStockKey());
        Assert.assertNotNull(read);
        Assert.assertEquals(stock.getQuantity(), read.getQuantity());
        Assert.assertEquals(9, stockRepository.findAll().size());

        stockRepository.deleteStockByStockKey(stock.getStockKey());
        Assert.assertEquals(8, stockRepository.findAll().size());
    }

    @Test
    public void testSingleLocationStrategy() {
        List<Location> locations = strategy.findLocation(getOrderDTO());

        Assert.assertNotNull(locations);
        Assert.assertEquals(1, locations.size());
        Assert.assertEquals(Integer.valueOf(203), locations.get(0).getId());
        Assert.assertEquals("Location 3", locations.get(0).getName());
    }

    @Test
    public void testCreateOrderWithSingleLocationStrategy() {

        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Test");
        customer.setPassword("test");
        customer.setUsername("testUser");
        customerService.save(customer);

        securityService.login("testUser", "test");

        Order order = orderService.createOrder(getOrderDTO());

        Assert.assertNotNull(order);
        Assert.assertNotNull(order.getId());

        Product product = productRepository.findProductById(201);
        Location location = locationRepository.findLocationById(203);
        StockKey stockKey = new StockKey();
        stockKey.setLocation(location);
        stockKey.setProduct(product);
        Stock stock = stockRepository.findStockByStockKey(stockKey);
        Assert.assertNotNull(stock);
        Assert.assertEquals(Integer.valueOf(2), stock.getQuantity());
    }

    @Test(expected = LocationNotFoundException.class)
    public void testCreateOrderWithSingleLocationStrategyException() {
        OrderDTO orderDTO = getOrderDTO();
        orderDTO.getProducts().get(0).setQuantity(14);
        orderService.createOrder(orderDTO);
    }

    @Test
    public void testExportStocks() {
        List<Stock> exported = stockService.exportStocks(203);

        Assert.assertNotNull(exported);
        Assert.assertEquals(3, exported.size());
    }

    private OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProducts(getProductsForSingleLocationStrategy());
        orderDTO.setDeliveryAddress(getAddressForSingleLocationStrategy());
        orderDTO.setOrderTime(LocalDateTime.now());
        return orderDTO;
    }

    private Address getAddressForSingleLocationStrategy() {
        return addressRepository.findAddressById(204);
    }

    private List<ProductDTO> getProductsForSingleLocationStrategy() {
        List<ProductDTO> result = new ArrayList<>();

        Product product1 = productRepository.findProductById(201);
        Product product2 = productRepository.findProductById(202);
        Product product3 = productRepository.findProductById(203);

        result.add(getProductDTOForSingleLocationStrategy(product1, 4));
        result.add(getProductDTOForSingleLocationStrategy(product2, 4));
        result.add(getProductDTOForSingleLocationStrategy(product3, 8));
        // with 4, 1, 1 as the quantities we should get back Location 2 (tested)

        return result;
    }

    private ProductDTO getProductDTOForSingleLocationStrategy(Product product, int quantity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProduct(product);
        productDTO.setQuantity(quantity);
        return productDTO;
    }
}
