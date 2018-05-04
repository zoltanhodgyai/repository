package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer shippedFrom;

    private Integer customer;

    private String addressCountry;

    private String addressCity;

    private String addressCounty;

    private String addressStreet;

    private Integer addressNumber;

}
