package ro.msg.learning.shop.web;

import org.hibernate.LazyInitializationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.exception.LocationIdMissingException;
import ro.msg.learning.shop.exception.NoStocksFoundException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.utility.CsvHandler;
import ro.msg.learning.shop.utility.DTOConverter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("/stockService")
public class StockServiceRestController {

    private static final String FILE_NAME = "exportedStocks.csv";

    private final StockService stockService;

    private final LocationRepository locationRepository;

    private final ProductRepository productRepository;

    private final CsvHandler<StockDTO> csvHandler;

    public StockServiceRestController(StockService stockService, LocationRepository locationRepository, ProductRepository productRepository, CsvHandler<StockDTO> csvHandler) {
        this.stockService = stockService;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
        this.csvHandler = csvHandler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void extractStocks(Integer locationId, HttpServletResponse httpOutputMessage) throws IOException {
        List<Stock> stocks = stockService.exportStocks(locationId);

        for (Stock stock : stocks) {
            try {
                stock.getLocation().getName();
            } catch (LazyInitializationException e) {
                stock.setLocation(locationRepository.findLocationById(stock.getLocation().getId()));
                stock.setProduct(productRepository.findProductById(stock.getProduct().getId()));
            }
        }

        List<StockDTO> stockDTOS = DTOConverter.toStockDTOS(stocks);

        if (locationId == null) {
            throw new LocationIdMissingException("Location id must be given!");
        }
        if (stockDTOS == null || stockDTOS.isEmpty()) {
            throw new NoStocksFoundException("No stocks found for the given location!");
        }

        csvHandler.writeInternal(stockDTOS, StockDTO.class, new HttpOutputMessage() {
            @Override
            public OutputStream getBody() throws IOException {
                return httpOutputMessage.getOutputStream();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "text.csv");
                return headers;
            }
        });
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
