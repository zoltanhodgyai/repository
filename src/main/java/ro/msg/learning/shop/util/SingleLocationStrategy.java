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
public class SingleLocationStrategy implements Strategy {

    private StockRepository stockRepository;

    private LocationRepository locationRepository;

    public SingleLocationStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findLocation(OrderDTO orderDTO) {
        log.info("Single Location Strategy called");
        List<Integer> result = new ArrayList<>();
        // TODO @hodgyaiz: refactor this, use something like this: Specifications Spring Data JPA (google it)
        if (!orderDTO.getProducts().isEmpty()) {
            // add all locations for the first product
            result.addAll(stockRepository.findLocationIdsByProductAndQuantity(orderDTO.getProducts().get(0).getProduct()
                    .getId(), orderDTO.getProducts().get(0).getQuantity()));
            // add the remaining locations if they are common
            for (ProductDTO productDTO : orderDTO.getProducts()) {
                result = leaveOnlyCommonElements(result, stockRepository.findLocationIdsByProductAndQuantity(productDTO
                        .getProduct().getId(), productDTO.getQuantity()));
            }
        }

        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        List<Location> finalResult = new ArrayList<>();
        finalResult.add(locationRepository.findLocationById(result.get(0)));
        return finalResult;
    }

    private List<Integer> leaveOnlyCommonElements(@NotNull List<Integer> l1, List<Integer> l2) {
        List<Integer> result = new ArrayList<>();
        for (Integer location : l1) {
            if (l2.contains(location) && !result.contains(location)) {
                result.add(location);
            }
        }
        return result;
    }

}
