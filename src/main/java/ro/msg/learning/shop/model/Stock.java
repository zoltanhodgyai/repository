package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
public class Stock {

    @EmbeddedId
    private StockKey stockKey;

    @PositiveOrZero
    private Integer quantity;

}
