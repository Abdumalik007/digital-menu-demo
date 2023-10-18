package food.system.repository;

import food.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryTest extends JpaRepository<User, Integer> {
}
