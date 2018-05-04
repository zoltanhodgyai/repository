package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String addressCountry;

    private String addressCity;

    private String addressCounty;

    private String addressStreet;

    private Integer addressNumber;

}
