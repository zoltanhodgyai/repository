package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailKey;
import ro.msg.learning.shop.model.Product;

import java.util.List;

public interface OrderDetailRepository extends Repository<OrderDetail, OrderDetailKey> {

    @Transactional
    OrderDetail save(OrderDetail orderDetail);

    @Transactional(readOnly = true)
    List<OrderDetail> findAll();

    @Transactional(readOnly = true)
    OrderDetail findOrderDetailByOrderAndProduct(Order order, Product product);

    @Transactional
    void deleteOrderDetailByOrderAndProduct(Order order, Product product);

    @Modifying
    @Query(value = "INSERT INTO ORDER_DETAIL (ORDER_NUMBER, PRODUCT, QUANTITY) VALUES (:orderNumber,:product,:quantity)", nativeQuery = true)
    @Transactional
    void saveCustom(@Param("orderNumber") Integer orderNumber, @Param("product") Integer productNumber, @Param("quantity") Integer quantity);
}
