package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.model.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class StockDTO {

    private Integer productId;

    private Integer locationId;

    private Integer quantity;

    private String productName;

    private String locationName;

    private BigDecimal productPrice;

    public static StockDTO toStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setLocationId(stock.getLocation().getId());
        stockDTO.setProductId(stock.getProduct().getId());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setLocationName(stock.getLocation().getName());
        stockDTO.setProductName(stock.getProduct().getName());
        stockDTO.setProductPrice(stock.getProduct().getPrice());
        return stockDTO;
    }

    public static List<StockDTO> toStockDTOS(List<Stock> stocks) {
        List<StockDTO> list = new ArrayList<>();
        for (Stock stock : stocks) {
            list.add(toStockDTO(stock));
        }
        return list;
    }
}
