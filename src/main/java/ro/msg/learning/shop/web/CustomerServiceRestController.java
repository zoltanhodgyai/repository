package ro.msg.learning.shop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.exception.UserNotLoggedInException;
import ro.msg.learning.shop.exception.UsernameAlreadyInUseException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.SecurityService;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
@Slf4j
public class CustomerServiceRestController {

    private final SecurityService securityService;

    private final CustomerService customerService;

    public CustomerServiceRestController(SecurityService securityService, CustomerService customerService) {
        this.securityService = securityService;
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            securityService.login(username, password);
            return String.format("Login for user %s successful!", username);
        } catch (UsernameNotFoundException e) {
            return "Username or password not correct!";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    public String registration(HttpServletRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setUsername(customer.getFirstName().toLowerCase() + "." + customer.getLastName().toLowerCase());
        customer.setPassword(request.getParameter("password"));

        try {
            customerService.save(customer);
            return String.format("Customer created with username %s successfully!", customer.getUsername());
        } catch (UsernameAlreadyInUseException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public String logout(@RequestParam String username) {
        try {
            securityService.logout(username);
            return "User logged out!";
        } catch (UserNotLoggedInException e) {
            return e.getMessage();
        }
    }
}
