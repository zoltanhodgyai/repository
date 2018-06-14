package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SingleLocation {

    List<Integer> findLocationIdsByProductAndQuantity(OrderDTO orderDTO, StockRepository stockRepository) {
        List<Integer> result = new ArrayList<>();
        if (!orderDTO.getProducts().isEmpty()) {
            result.addAll(stockRepository.findLocationIdsByProductAndQuantity(orderDTO.getProducts().get(0).getProduct().getId(),
                    orderDTO.getProducts().get(0).getQuantity()));

            orderDTO.getProducts().remove(0);
            for (ProductDTO productDTO : orderDTO.getProducts()) {
                result = stockRepository.findLocationIdsByProductAndQuantityAndLocationIds(productDTO.getProduct().getId(),
                        productDTO.getQuantity(), result);
            }
        }
        return result;
    }
}
