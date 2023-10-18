package food.system.repository;

import food.system.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
    boolean existsByName(String name);
    boolean existsByUserUsername(String username);
    boolean existsByUserUsernameAndIdIsNot(String username, Integer id);
}
