package ro.msg.learning.shop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.UsernameAlreadyInUseException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Customer save(Customer customer) {
        if (findCustomerByUsername(customer.getUsername()) != null) {
            throw new UsernameAlreadyInUseException("Customer already exists!");
        }
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer findCustomerByUsername(String username) {
        return customerRepository.findCustomerByUsername(username);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Integer id) {
        return customerRepository.findCustomerById(id);
    }

    public void deleteCustomerById(Integer id) {
        customerRepository.deleteCustomerById(id);
    }
}
