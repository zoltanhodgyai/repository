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
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;

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

    @Test
    public void testStockFull() {
        Location location = locationRepository.findLocationById(100);
        Assert.assertNotNull(location);
        Product product = productRepository.findProductById(100);
        Assert.assertNotNull(product);

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(12);

        stockRepository.saveCustom(stock.getProduct().getId(), stock.getLocation().getId(), stock.getQuantity());
        // TODO @hodgyaiz: simple save(Stock) not working. Use saveCustom instead
        //OrderDetail created = orderDetailRepository.save(orderDetail);
        //Assert.assertNotNull(created);

        Stock read = stockRepository.findStockByProductAndLocation(stock.getProduct(), stock.getLocation());
        Assert.assertNotNull(read);
        Assert.assertEquals(stock.getQuantity(), read.getQuantity());
        Assert.assertEquals(1, stockRepository.findAll().size());

        stockRepository.deleteStockByProductAndLocation(stock.getProduct(), stock.getLocation());
        Assert.assertEquals(0, stockRepository.findAll().size());
    }
}
