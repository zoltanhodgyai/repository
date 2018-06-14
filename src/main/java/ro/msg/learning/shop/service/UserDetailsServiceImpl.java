package ro.msg.learning.shop.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Customer;

import java.util.HashSet;
import java.util.Set;

@Service(value = "userService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String CUSTOMER_ROLE = "CustomerRole";

    private final CustomerService customerService;

    public UserDetailsServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.findCustomerByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (customer != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(CUSTOMER_ROLE));
        } else {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), grantedAuthorities);
    }
}
