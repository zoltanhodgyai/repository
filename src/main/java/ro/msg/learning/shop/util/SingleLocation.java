package ro.msg.learning.shop.util;

import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.repository.StockRepository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SingleLocation {

    List<Integer> findLocationIdsByProductAndQuantity(OrderDTO orderDTO, StockRepository stockRepository) {
        // TODO @hodgyaiz: refactor this, use something like this: Specifications Spring Data JPA (google it)
        List<Integer> result = new ArrayList<>();
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

        return result;
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
