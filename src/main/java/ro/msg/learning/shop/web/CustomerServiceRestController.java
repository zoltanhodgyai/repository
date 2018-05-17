package ro.msg.learning.shop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.exception.UserNotLoggedInException;
import ro.msg.learning.shop.exception.UsernameAlreadyInUseException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.SecurityService;

@ControllerAdvice
@RestController
public class CustomerServiceRestController {

    private final SecurityService securityService;

    private final CustomerService customerService;

    public CustomerServiceRestController(SecurityService securityService, CustomerService customerService) {
        this.securityService = securityService;
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            securityService.login(username, password);
            return new ResponseEntity<>(String.format("Login for user %s successfully!", username), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Username or password not correct!", HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/registration")
    public ResponseEntity<String> registration(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setUsername(firstName.toLowerCase() + "." + lastName.toLowerCase());
        customer.setPassword(password);

        try {
            customerService.save(customer);
            return new ResponseEntity<>(String.format("Customer created with username %s successfully!", customer.getUsername()), HttpStatus.OK);
        } catch (UsernameAlreadyInUseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ResponseEntity<String> logout(@RequestParam String username) {
        // todo @hodgyaiz: something happened. When I call the service the result is directly /login
        // after that, when I try to do something the user is signed out.
        try {
            securityService.logout(username);
            return new ResponseEntity<>("User logged out!", HttpStatus.OK);
        } catch (UserNotLoggedInException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
