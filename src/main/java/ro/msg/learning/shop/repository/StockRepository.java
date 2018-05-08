package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockKey;

import java.util.List;

public interface StockRepository extends Repository<Stock, StockKey> {

    @Transactional
    Stock save(Stock stock);

    @Transactional(readOnly = true)
    List<Stock> findAll();

    @Transactional
    void deleteStockByProductAndLocation(Product product, Location location);

    @Transactional(readOnly = true)
    Stock findStockByProductAndLocation(Product product, Location location);

    @Modifying
    @Query(value = "INSERT INTO STOCK (PRODUCT, LOCATION, QUANTITY) VALUES (:product,:location,:quantity)", nativeQuery = true)
    @Transactional
    void saveCustom(@Param("product") Integer productNumber, @Param("location") Integer locationNumber, @Param("quantity") Integer quantity);
}
