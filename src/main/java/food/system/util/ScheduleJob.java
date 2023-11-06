package food.system.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static food.system.controller.StatisticController.USER_AMOUNT;



@Component
public class ScheduleJob {

    @Scheduled(cron = "59 59 23 * * *", zone = "Asia/Bishkek")
    public void doSomethingEachDayAtMidnight() {
        System.err.println(new Date() + " CLEARED");
        USER_AMOUNT.clear();
    }

}
