package ro.msg.learning.shop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Revenue>> aggregateSalesRevenuesForDate(@RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Revenue> result = revenueService.aggregateSalesRevenuesForDate(dateTime);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
