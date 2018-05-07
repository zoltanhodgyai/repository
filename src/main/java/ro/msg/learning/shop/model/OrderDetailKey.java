package ro.msg.learning.shop.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailKey implements Serializable {

    private Order order;
    private Product product;
}
