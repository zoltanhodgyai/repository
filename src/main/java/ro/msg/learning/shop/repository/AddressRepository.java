package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Address;

import java.util.List;

public interface AddressRepository extends Repository<Address, Integer> {

    @Transactional
    Address save(Address address);

    @Transactional(readOnly = true)
    List<Address> findAll();

    @Transactional
    void deleteAddressById(Integer id);

    @Transactional(readOnly = true)
    Address findAddressById(Integer id);
}
