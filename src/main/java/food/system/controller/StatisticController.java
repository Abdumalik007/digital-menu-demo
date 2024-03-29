package food.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/statistic")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StatisticController {
    public static HashSet<String> USER_AMOUNT = new HashSet<>();

    @GetMapping("/user-amount")
    public Integer getUserAmount() {
        return USER_AMOUNT.size();
    }
}
