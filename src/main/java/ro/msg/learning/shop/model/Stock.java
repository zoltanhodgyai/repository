package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
public class Stock {

    @EmbeddedId
    private StockKey stockKey;

    @Positive
    private Integer quantity;

}
