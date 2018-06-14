package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RevenueService {

    private final RevenueRepository revenueRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    public RevenueService(RevenueRepository revenueRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository) {
        this.revenueRepository = revenueRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Revenue> aggregateSalesRevenuesForDate(LocalDateTime date) {
        Map<Location, Revenue> revenuesWithLocationsMap = new HashMap<>();
        for (Order order : orderRepository.findAllByOrderDateTimeIsBetween(date.minusDays(1), date)) {
            Double sum = 0.0;
            for (OrderDetail orderDetail : orderDetailRepository.findAllByOrder(order)) {
                sum = sum + (Double.valueOf(orderDetail.getQuantity()) *
                        productRepository.findProductById(orderDetail.getProduct().getId()).getPrice().doubleValue());
            }
            Revenue revenue;
            if (revenuesWithLocationsMap.get(order.getShippedFrom()) == null) {
                revenue = new Revenue();
                revenue.setSum(BigDecimal.valueOf(sum));

                revenuesWithLocationsMap.put(order.getShippedFrom(), revenue);
            } else {
                revenue = revenuesWithLocationsMap.get(order.getShippedFrom());
                revenue.setSum(revenue.getSum().add(BigDecimal.valueOf(sum)));
            }
            revenue.setDate(date);
            revenue.setLocation(order.getShippedFrom());
        }
        return revenuesWithLocationsMap.values().stream().map(revenueRepository::save).collect(Collectors.toList());
    }
}
