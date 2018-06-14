package ro.msg.learning.shop.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.service.RevenueService;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("/revenueService")
public class RevenueServiceRestController {

    private final RevenueService revenueService;

    public RevenueServiceRestController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Revenue> aggregateSalesRevenuesForDate(@RequestParam
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {

        return revenueService.aggregateSalesRevenuesForDate(dateTime);
    }
}
