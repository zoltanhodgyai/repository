package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailKey;

import java.util.List;

public interface OrderDetailRepository extends Repository<OrderDetail, OrderDetailKey> {

    @Transactional
    OrderDetail save(OrderDetail orderDetail);

    @Transactional(readOnly = true)
    List<OrderDetail> findAll();

    @Transactional(readOnly = true)
    OrderDetail findOrderDetailByOrderDetailKey(OrderDetailKey orderDetailKey);

    @Transactional
    void deleteOrderDetailByOrderDetailKey(OrderDetailKey orderDetailKey);

}
