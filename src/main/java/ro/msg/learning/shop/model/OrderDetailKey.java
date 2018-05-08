package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class OrderDetailKey implements Serializable {

    private Order order;
    private Product product;
}
