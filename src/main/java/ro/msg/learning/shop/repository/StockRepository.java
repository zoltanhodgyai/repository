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

    @Transactional
    void deleteStockByProductAndLocation(Product product, Location location);

    @Transactional(readOnly = true)
    Stock findStockByProductAndLocation(Product product, Location location);

    @Transactional(readOnly = true)
    List<Stock> findAllByLocation(Location location);

    @Transactional(readOnly = true)
    @Query(value = "SELECT s.location.id FROM Stock s WHERE s.product.id = :product AND s.quantity >= :quantity")
    List<Integer> findLocationIdsByProductAndQuantity(@Param("product") Integer productNumber, @Param("quantity") Integer quantity);

    @Transactional(readOnly = true)
    @Query(value = "SELECT s.location.id FROM Stock s WHERE s.product.id = :product AND s.quantity >= :quantity AND s.location.id IN (:locations)")
    List<Integer> findLocationIdsByProductAndQuantityAndLocationIds(@Param("product") Integer productNumber,
                                                                    @Param("quantity") Integer quantity,
                                                                    @Param("locations") List<Integer> locations);

}
