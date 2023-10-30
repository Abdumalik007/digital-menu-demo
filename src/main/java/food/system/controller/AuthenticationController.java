package food.system.controller;

import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import food.system.service.main.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


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
    public void logout(HttpServletRequest request, HttpServletResponse response){
        userService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

}
















