package ro.msg.learning.shop.model;

import lombok.Data;
import org.apache.olingo.odata2.api.annotation.edm.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Data
@EdmEntityType(name = "OrderDetail")
@EdmEntitySet(name = "OrderDetails")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EdmKey
    @EdmProperty
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_number")
    @EdmNavigationProperty(name = "Order", toType = Order.class)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_number")
    @EdmNavigationProperty(name = "Product", toType = Product.class)
    private Product product;

    @Positive
    @EdmProperty
    private Integer quantity;

}
