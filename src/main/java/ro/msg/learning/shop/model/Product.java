package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    // EUR
    private BigDecimal price;
    // KG
    private Double weight;
    @ManyToOne
    @JoinColumn(name = "category")
    private ProductCategory category;
    @ManyToOne
    @JoinColumn(name = "supplier")
    private Supplier supplier;

}
