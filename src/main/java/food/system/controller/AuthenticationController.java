package food.system.controller;

import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import food.system.service.main.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletRequest request){
        return userService.login(loginRequest, request);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        return userService.logout(request);
    }


//    @GetMapping("/access-denied")
//    public String accessDeniedHandler(HttpServletResponse response) throws IOException {
//        return "{\"error\": \"Token expired\"}";
//    }

}
















