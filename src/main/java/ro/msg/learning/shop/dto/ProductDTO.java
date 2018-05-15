package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.model.Product;

@Data
public class ProductDTO {

    private Product product;

    private int quantity;
}
