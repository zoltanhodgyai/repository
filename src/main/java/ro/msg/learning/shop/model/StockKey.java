package ro.msg.learning.shop.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockKey implements Serializable {

    private Product product;
    private Location location;
}
