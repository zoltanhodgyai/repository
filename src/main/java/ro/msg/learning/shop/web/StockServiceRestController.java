package ro.msg.learning.shop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.exception.LocationIdMissingException;
import ro.msg.learning.shop.exception.NoStocksFoundException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@RequestMapping("/stockService")
public class StockServiceRestController {

    private final StockService stockService;

    public StockServiceRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "text/csv")
    public List<StockDTO> extractStocks(Integer locationId) {
        if (locationId == null) {
            throw new LocationIdMissingException("Location id must be given!");
        }
        List<Stock> stocks = stockService.exportStocks(locationId);
        if (stocks == null || stocks.isEmpty()) {
            throw new NoStocksFoundException("No stocks found for the given location!");
        }

        return stocks.stream().map(StockDTO::toStockDTO).collect(Collectors.toList());
    }

    @ExceptionHandler(LocationIdMissingException.class)
    public ResponseEntity<String> handleLocationIdMissingException(final LocationIdMissingException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoStocksFoundException.class)
    public ResponseEntity<String> handleNoStocksFoundException(final NoStocksFoundException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
