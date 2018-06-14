package ro.msg.learning.shop.dto;

import lombok.Data;

@Data
public class ElementDTO {

    private DistanceDTO distance;

    private DurationDTO duration;

    private String status;
}
