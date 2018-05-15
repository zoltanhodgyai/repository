package ro.msg.learning.shop.util;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Location;

import java.util.List;

@Component
public interface Strategy {

    List<Location> findLocation(OrderDTO orderDTO);
}
