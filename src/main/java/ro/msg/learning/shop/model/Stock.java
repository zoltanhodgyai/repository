package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@IdClass(StockKey.class)
public class Stock {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product")
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location")
    private Location location;

    private Integer quantity;

}
