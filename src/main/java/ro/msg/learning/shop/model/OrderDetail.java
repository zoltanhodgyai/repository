package ro.msg.learning.shop.model;

import lombok.Data;

@Data
public class OrderDetail {

    private Integer order;

    private Integer product;

    private Integer quantity;

}
