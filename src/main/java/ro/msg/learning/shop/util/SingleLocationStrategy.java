package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("SingleLocationStrategy")
@Slf4j
public class SingleLocationStrategy extends SingleLocation implements Strategy {

    private StockRepository stockRepository;

    private LocationRepository locationRepository;

    public SingleLocationStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findLocation(OrderDTO orderDTO) {
        log.info("Single Location Strategy called");
        List<Integer> result = findLocationIdsByProductAndQuantity(orderDTO, stockRepository);

        if (result.isEmpty()) {
            return null;
        }
        return locationRepository.findLocationById(result.get(0));
    }

}
