package food.system.security.jwt;

import food.system.entity.User;
import food.system.redis.UserSession;
import food.system.redis.UserSessionRedisRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserSessionRedisRepository redisRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String token;
        final String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            try {
                token = authHeader.substring(7);
                boolean nonExpired = jwtService.isTokenNonExpired(token);
                if(!nonExpired) throw new RuntimeException("Token has expired or invalid");

                String uuid = jwtService.extractSubject(token);

                Optional<UserSession> optionalUser = redisRepository.findById(uuid);
                if (optionalUser.isEmpty()) {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    throw new RuntimeException("Token has expired or invalid");
                }
                User user = optionalUser.get().getValue();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                token,
                                user.getAuthorities()
                        );
                authenticationToken.setDetails(request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (RuntimeException e){
                logger.error(e.getMessage());
                SecurityContextHolder.getContext().setAuthentication(null);
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
        filterChain.doFilter(request, response);
    }



}

















