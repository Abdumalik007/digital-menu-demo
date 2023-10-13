package food.system.util;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static food.system.controller.StatisticController.USER_AMOUNT;


@EnableScheduling
@Component
public class ScheduleJob {
    @Scheduled(cron = "0 0 * * * ?")
    public void doSomethingEachDayAtMidnight() {
        USER_AMOUNT.clear();
    }
}
