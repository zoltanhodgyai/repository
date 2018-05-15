package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
public class OrderDetail {

    @EmbeddedId
    private OrderDetailKey orderDetailKey;

    @Positive
    private Integer quantity;

}
