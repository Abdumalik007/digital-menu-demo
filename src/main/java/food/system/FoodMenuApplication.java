package food.system;

import food.system.entity.Admin;
import food.system.entity.User;
import food.system.repository.AdminRepository;
import food.system.role.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class FoodMenuApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodMenuApplication.class, args);
    }


    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        if(adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .user(
                            User.builder()
                                    .username("admin")
                                    .password(encoder.encode("123456"))
                                    .role(Role.ADMIN).build()
                    )
                    .name("Falonchi")
                    .build();
            adminRepository.save(admin);
        }
    }

}
