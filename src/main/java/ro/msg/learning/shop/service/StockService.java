package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    private final LocationRepository locationRepository;

    public StockService(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    public List<Stock> exportStocks(Integer locationId) {
        return stockRepository.findAllByLocation(locationRepository.findLocationById(locationId));
    }
}
