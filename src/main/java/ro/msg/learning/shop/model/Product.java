package ro.msg.learning.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.olingo.odata2.api.annotation.edm.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EdmEntityType(name = "Product")
@EdmEntitySet(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EdmKey
    @EdmProperty
    private Integer id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String description;

    @EdmProperty
    private BigDecimal price;

    @EdmProperty
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @EdmNavigationProperty(name = "ProductCategory", toType = ProductCategory.class)
    @JoinColumn(name = "productCategory")
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @EdmNavigationProperty(name = "Supplier", toType = Supplier.class)
    @JoinColumn(name = "supplier")
    private Supplier supplier;

}
