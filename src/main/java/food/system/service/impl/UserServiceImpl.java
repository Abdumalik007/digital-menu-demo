package food.system.service.impl;


import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import food.system.entity.User;
import food.system.redis.UserSession;
import food.system.redis.UserSessionRedisRepository;
import food.system.repository.UserRepository;
import food.system.security.role.Role;
import food.system.security.jwt.JwtUtil;
import food.system.service.main.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static food.system.helper.ResponseEntityHelper.INTERNAL_ERROR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRedisRepository redisRepository;
    private final JwtUtil jwtUtil;


    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findUserByUsername(loginRequest.getUsername());

        if(userOptional.isEmpty())
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();

        if(!passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword()))
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userOptional.get(),
                        null,
                        userOptional.get().getAuthorities()
                );

        authenticationToken.setDetails(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        String uuid = UUID.randomUUID().toString();

        UserSession userSession = UserSession.builder()
                .id(uuid).value(userOptional.get()).build();

        try {
            redisRepository.save(userSession);
        }catch (Exception e){
            logger.error("Error while logging in: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }

        String jwtToken = jwtUtil.generateToken(uuid);

        LoginResponse response = new LoginResponse();
        Role role = userOptional.get().getRole();
        response.setRole(role.name());
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            String uuid = jwtUtil.extractSubject(token);
            redisRepository.deleteById(uuid);
        }catch (Exception e){
            logger.error("Error while logging out!");
        }
        return ResponseEntity.ok("Ok");
    }


}


