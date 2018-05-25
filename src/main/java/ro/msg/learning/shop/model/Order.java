package ro.msg.learning.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.olingo.odata2.api.annotation.edm.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "OrderTable")
@EdmEntityType(name = "Order")
@EdmEntitySet(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EdmKey
    @EdmProperty
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @EdmNavigationProperty(name = "ShippedFrom", toType = Location.class)
    @JoinColumn(name = "shipped_from")
    private Location shippedFrom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @EdmNavigationProperty(name = "Customer", toType = Customer.class)
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @EdmNavigationProperty(name = "Address", toType = Address.class)
    @JoinColumn(name = "address")
    private Address address;

    @EdmProperty(type = EdmType.DATE_TIME)
    private LocalDateTime orderDateTime;
}
