package ro.msg.learning.shop.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.util.Strategy;

@Service
@Transactional
public class OrderService {

    private final StockRepository stockRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final CustomerRepository customerRepository;

    private final SecurityService securityService;

    private final Strategy strategy;

    public OrderService(StockRepository stockRepository, OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository, CustomerRepository customerRepository,
                        SecurityService securityService, Strategy strategy) {
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository = customerRepository;
        this.securityService = securityService;
        this.strategy = strategy;
    }

    public Order createOrder(OrderDTO orderDTO) {

        // get the single location where all of the products are found
        Location location = strategy.findLocation(orderDTO);
        if (location == null) {
            throw new LocationNotFoundException("No location was found!");
        }
        // update the stock. Quantity must be updated
        orderDTO.getProducts().forEach(
                product -> {
                    Stock stock = stockRepository.findStockByProductAndLocation(product.getProduct(), location);
                    stock.setQuantity(stock.getQuantity() - product.getQuantity());
                    stockRepository.save(stock);
                }
        );
        // save the new Order
        Order order = new Order();
        order.setShippedFrom(location);
        order.setAddress(orderDTO.getDeliveryAddress());
        order.setOrderDateTime(orderDTO.getOrderTime());
        String username = securityService.findLoggedInUsername();
        if (username == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        order.setCustomer(customerRepository.findCustomerByUsername(username));
        final Order savedOrder = orderRepository.save(order);
        // save the Order Details (for every product)
        orderDTO.getProducts().forEach(
                product -> orderDetailRepository.save(new OrderDetail(null, savedOrder, product.getProduct(), product.getQuantity()))
        );

        return order;
    }
}
