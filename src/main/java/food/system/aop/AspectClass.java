package food.system.aop;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static food.system.controller.StatisticController.USER_AMOUNT;

@Aspect
@Component
@Order(1)
public class AspectClass {
    @Before("execution(* food.system.controller.CategoryController.getAllCategoriesWithFoods(..)) && args(request,..)")
    public void getIPAddress(HttpServletRequest request) {
        USER_AMOUNT.add(request.getRemoteAddr());
    }

}
