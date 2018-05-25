package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;

import java.util.List;

public interface OrderDetailRepository extends Repository<OrderDetail, Integer> {

    @Transactional
    OrderDetail save(OrderDetail orderDetail);

    @Transactional(readOnly = true)
    List<OrderDetail> findAll();

    @Transactional(readOnly = true)
    OrderDetail findOrderDetailById(Integer id);

    @Transactional(readOnly = true)
    OrderDetail findOrderDetailByOrderAndProduct(Order order, Product product);

    @Transactional
    void deleteOrderDetailById(Integer id);

    @Transactional
    void deleteOrderDetailByOrderAndProduct(Order order, Product product);

    @Transactional
    List<OrderDetail> findAllByOrder(Order order);

}
