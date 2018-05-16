package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Location;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("MultipleLocationStrategy")
@Slf4j
public class MultipleLocationStrategy implements Strategy {

    @Override
    public Location findLocation(OrderDTO orderDTO) {
        log.info("Multiple Location Strategy called");
        log.info("Not implemented yet.");
        return null;
    }
}
