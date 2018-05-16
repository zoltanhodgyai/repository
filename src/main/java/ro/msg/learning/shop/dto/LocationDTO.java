package ro.msg.learning.shop.dto;

import lombok.Data;

@Data
public class LocationDTO {

    private String[] destination_addresses;

    private String[] origin_addresses;

    private Row[] rows;
}
