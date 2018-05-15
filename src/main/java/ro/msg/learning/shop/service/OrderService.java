package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.configuration.StrategyConfiguration;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;

@Service
public class OrderService {

    @Value("${shop.strategy}")
    String strategyType;

    private final StockRepository stockRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final CustomerRepository customerRepository;

    private final StrategyConfiguration strategyConfiguration;

    public OrderService(StockRepository stockRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, CustomerRepository customerRepository, StrategyConfiguration strategyConfiguration) {
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.customerRepository = customerRepository;
        this.strategyConfiguration = strategyConfiguration;
    }

    public Order createOrder(OrderDTO orderDTO) throws LocationNotFoundException {

        // get the single location where all of the products are found
        List<Location> locations = strategyConfiguration.getStrategy(strategyType).findLocation(orderDTO);
        if (locations == null || locations.isEmpty()) {
            throw new LocationNotFoundException("No location was found!");
        }
        // TODO @hodgyaiz: now we know that we have only one location. The logic must be updated if another strategy is used
        Location location = locations.get(0);
        // update the stock. Quantity must be updated
        for (ProductDTO product : orderDTO.getProducts()) {
            StockKey stockKey = new StockKey();
            stockKey.setProduct(product.getProduct());
            stockKey.setLocation(location);
            Stock stock = stockRepository.findStockByStockKey(stockKey);
            stock.setQuantity(stock.getQuantity() - product.getQuantity());
            stockRepository.save(stock);
        }
        // save the new Order
        Order order = new Order();
        order.setShippedFrom(location);
        order.setAddress(orderDTO.getDeliveryAddress());
        // TODO @hodgyaiz: for tests we just read a customer (id = 100)
        // o sa luam din user (clientul logat)
        order.setCustomer(customerRepository.findCustomerById(100));
        order = orderRepository.save(order);
        // save the Order Details (for every product)
        for (ProductDTO product : orderDTO.getProducts()) {
            OrderDetail orderDetail = new OrderDetail();
            OrderDetailKey orderDetailKey = new OrderDetailKey();
            orderDetailKey.setProduct(product.getProduct());
            orderDetailKey.setOrder(order);
            orderDetail.setQuantity(product.getQuantity());
            orderDetail.setOrderDetailKey(orderDetailKey);
            orderDetailRepository.save(orderDetail);
        }

        return order;
    }
}
