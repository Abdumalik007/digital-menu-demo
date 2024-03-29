package food.system.service.main;

import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutHandler;


public interface UserService {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletRequest request);
    ResponseEntity<?> logout(HttpServletRequest request);
}
