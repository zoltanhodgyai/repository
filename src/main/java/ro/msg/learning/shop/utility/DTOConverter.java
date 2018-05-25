package ro.msg.learning.shop.utility;

import org.hibernate.LazyInitializationException;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class DTOConverter {

    public static StockDTO toStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setLocationId(stock.getLocation().getId());
        stockDTO.setProductId(stock.getProduct().getId());
        stockDTO.setQuantity(stock.getQuantity());
        try {
            stockDTO.setLocationName(stock.getLocation().getName());
            stockDTO.setProductName(stock.getProduct().getName());
            stockDTO.setProductPrice(stock.getProduct().getPrice());
        } catch (LazyInitializationException e) {
            // do nothing
        }
        return stockDTO;
    }

    public static List<StockDTO> toStockDTOS(List<Stock> stocks) {
        List<StockDTO> list = new ArrayList<>();
        for (Stock stock : stocks) {
            list.add(toStockDTO(stock));
        }
        return list;
    }

    public static Stock fromStockDTO(StockDTO stockDTO) {

        Stock stock = new Stock();
        stock.setQuantity(stockDTO.getQuantity());
        stock.setProduct(new Product());
        stock.getProduct().setId(stockDTO.getProductId());
        stock.setLocation(new Location());
        stock.getLocation().setId(stockDTO.getLocationId());

        return stock;
    }

    public static List<Stock> fromStockDTOS(List<StockDTO> stockDTOS) {
        List<Stock> list = new ArrayList<>();
        for (StockDTO stockDTO : stockDTOS) {
            list.add(fromStockDTO(stockDTO));
        }
        return list;
    }
}
