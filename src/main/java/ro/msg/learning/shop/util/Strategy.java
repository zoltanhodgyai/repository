package ro.msg.learning.shop.util;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Location;

@Component
public interface Strategy {

    Location findLocation(OrderDTO orderDTO);
}
