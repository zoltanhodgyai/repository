package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
public class ScheduledTasks {

//    @Scheduled(fixedRate = 20000)
    @Scheduled(cron = "59 59 23 * * ?") // every day at 23:59:59
    @Scheduled(cron = "59 05 12 * * ?") // every day at 12:05:59 only an example
    public void reportCurrentTime() {
        log.info("LocalTime is: " + LocalTime.now().toString());
    }

}
