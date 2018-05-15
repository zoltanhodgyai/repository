package ro.msg.learning.shop.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Customer;

import java.util.List;

public interface CustomerRepository extends Repository<Customer, Integer> {

    @Transactional
    Customer save(Customer customer);

    @Transactional(readOnly = true)
    Customer findCustomerById(Integer id);

    @Transactional(readOnly = true)
    List<Customer> findAll();

    @Transactional
    void deleteCustomerById(Integer id);

    @Transactional(readOnly = true)
    Customer findCustomerByUsername(String username);
}
