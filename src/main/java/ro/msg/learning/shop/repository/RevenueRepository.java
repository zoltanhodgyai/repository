package ro.msg.learning.shop.repository;

import org.apache.tomcat.jni.Local;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Revenue;

import java.time.LocalDateTime;
import java.util.List;

public interface RevenueRepository extends Repository<Revenue, Integer> {

    @Transactional
    Revenue save(Revenue revenue);

    @Transactional(readOnly = true)
    Revenue findRevenueById(Integer id);

    @Transactional(readOnly = true)
    List<Revenue> findAll();

    @Transactional(readOnly = true)
    List<Revenue> findAllByLocation(Integer locationNumber);

    @Transactional(readOnly = true)
    List<Revenue> findAllByDateBefore(LocalDateTime date);

    @Transactional(readOnly = true)
    List<Revenue> findAllByDateBetween(LocalDateTime after, LocalDateTime before);

    @Transactional(readOnly = true)
    List<Revenue> findAllByDateBeforeAndDateAfter(LocalDateTime after, LocalDateTime before);
}
