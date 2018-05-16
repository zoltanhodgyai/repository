package ro.msg.learning.shop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.service.OrderService;

@ControllerAdvice
@RestController
@RequestMapping("/orderService")
public class OrderServiceRestController {

    private final OrderService orderService;

    public OrderServiceRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);

        //order.getShippedFrom().setAddress(null);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
