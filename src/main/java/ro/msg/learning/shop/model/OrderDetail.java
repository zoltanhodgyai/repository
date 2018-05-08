package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@IdClass(OrderDetailKey.class)
public class OrderDetail {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_number")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product")
    private Product product;

    private Integer quantity;

}
