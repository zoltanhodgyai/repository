package ro.msg.learning.shop.util;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Location;

@Component
public class StrategyContext {

    // todo @hodgyaiz: delete this class???

    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Location findLocation(OrderDTO orderDTO) {
        return strategy.findLocation(orderDTO);
    }
}
