package ro.msg.learning.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockDTO {

    private Integer productId;

    private Integer locationId;

    private Integer quantity;

    private String productName;

    private String locationName;

    private BigDecimal productPrice;
}
