package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.model.Address;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private LocalDateTime orderTime;

    private Address deliveryAddress;

    private List<ProductDTO> products;


}
