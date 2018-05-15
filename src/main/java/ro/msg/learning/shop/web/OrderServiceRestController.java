package ro.msg.learning.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) throws LocationNotFoundException {
        // todo @hodgyaiz: put back in parameters: @RequestBody OrderDTO orderDTO
        // how do we construct a JSON request correctly? :P
        // how do we resolve the JavassistLazyInitializer problem?

        Order order = orderService.createOrder(orderDTO);

        //order.getShippedFrom().setAddress(null);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
