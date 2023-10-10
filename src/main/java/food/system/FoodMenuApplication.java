package food.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodMenuApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodMenuApplication.class, args);
    }
}
