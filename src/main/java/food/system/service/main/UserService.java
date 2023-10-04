package food.system.service.main;

import food.system.dto.custom.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;


public interface UserService{
    ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request);
}
