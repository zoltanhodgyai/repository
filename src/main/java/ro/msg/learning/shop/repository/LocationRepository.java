package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Location;

import java.util.List;

public interface LocationRepository extends Repository<Location, Integer> {

    @Transactional
    Location save(Location location);

    @Transactional(readOnly = true)
    Location findLocationById(Integer id);

    @Transactional(readOnly = true)
    List<Location> findAll();

    @Transactional
    void deleteLocationById(Integer id);
}
