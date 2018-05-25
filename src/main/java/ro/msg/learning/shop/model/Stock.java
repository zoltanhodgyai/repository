package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_number")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_number")
    private Location location;

    @PositiveOrZero
    private Integer quantity;

}
