package food.system.service.main;

import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;


public interface UserService{
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletRequest request);
}
