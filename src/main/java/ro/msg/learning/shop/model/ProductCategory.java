package ro.msg.learning.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.olingo.odata2.api.annotation.edm.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EdmEntityType(name = "ProductCategory")
@EdmEntitySet(name = "ProductCategories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EdmKey
    @EdmProperty
    private Integer id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategory")
    @EdmNavigationProperty(name = "Products", toType = Product.class)
    private List<Product> products;

}
