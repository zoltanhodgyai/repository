package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends Repository<Order, Integer> {

    @Transactional
    Order save(Order order);

    @Transactional(readOnly = true)
    Order findOrderById(Integer id);

    @Transactional(readOnly = true)
    List<Order> findAll();

    @Transactional
    void deleteOrderById(Integer id);

    @Transactional(readOnly = true)
    List<Order> findAllByOrderDateTimeIsBefore(LocalDateTime localDateTime);

    @Transactional(readOnly = true)
    List<Order> findAllByOrderDateTimeIsBetween(LocalDateTime from, LocalDateTime to);

    @Transactional(readOnly = true)
    List<Order> findAllByOrderDateTimeAfterAndOrderDateTimeBefore(LocalDateTime after, LocalDateTime before);
}
