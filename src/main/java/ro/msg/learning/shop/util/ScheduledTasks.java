package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.RevenueService;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ScheduledTasks {

    private final RevenueService revenueService;

    public ScheduledTasks(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @Scheduled(cron = "59 59 23 * * ?") // every day at 23:59:59
//    @Scheduled(cron = "01 15 16 * * ?") // every day at 16:15:01 only an example but it works :D
    public void reportCurrentTime() {

        log.info("Sales revenue created at: " + LocalDateTime.now());
        revenueService.aggregateSalesRevenuesForDate(LocalDateTime.now());
    }

}
