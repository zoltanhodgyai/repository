package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;

public interface StockRepository extends Repository<Stock, Integer> {

    @Transactional
    Stock save(Stock stock);

    @Transactional(readOnly = true)
    List<Stock> findAll();

    @Transactional(readOnly = true)
    Stock findStockById(Integer id);

    @Transactional
    void deleteStockById(Integer id);

    @Transactional
    void deleteStockByProductAndLocation(Product product, Location location);

    @Transactional(readOnly = true)
    Stock findStockByProductAndLocation(Product product, Location location);

    @Transactional(readOnly = true)
    @Query(value = "SELECT LOCATION_NUMBER FROM STOCK WHERE PRODUCT_NUMBER = :product AND QUANTITY >= :quantity", nativeQuery = true)
    List<Integer> findLocationIdsByProductAndQuantity(@Param("product") Integer productNumber, @Param("quantity") Integer quantity);

    @Transactional(readOnly = true)
    List<Stock> findAllByLocation(Location location);
}
