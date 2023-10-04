package food.system.redis;


import food.system.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "userSession", timeToLive = 60 * 60 * 24)
public class UserSession {
    @Id
    private String id;

    private User value;
}

