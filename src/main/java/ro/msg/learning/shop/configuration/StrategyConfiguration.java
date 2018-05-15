package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.util.MultipleLocationStrategy;
import ro.msg.learning.shop.util.SingleLocationStrategy;
import ro.msg.learning.shop.util.Strategy;

@Configuration
public class StrategyConfiguration {

    private static final String SINGLE_LOCATION_STRATEGY = "SingleLocationStrategy";
    private static final String MULTIPLE_LOCATION_STRATEGY = "MultipleLocationStrategy";

    private final StockRepository stockRepository;

    private final LocationRepository locationRepository;

    public StrategyConfiguration(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    @Primary
    @Bean
    public Strategy getStrategy(@Value("${shop.strategy}") String strategyType) {
            switch (strategyType) {
                case SINGLE_LOCATION_STRATEGY:
                    return new SingleLocationStrategy(stockRepository, locationRepository);
                case MULTIPLE_LOCATION_STRATEGY:
                    return new MultipleLocationStrategy();
                default:
                    return new SingleLocationStrategy(stockRepository, locationRepository);
            }
    }
}
