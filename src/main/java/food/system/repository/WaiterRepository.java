package food.system.repository;

import food.system.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
    List<Waiter> findAllByNameContainsIgnoreCase(String name);
    boolean existsByUserUsername(String username);
    boolean existsByUserUsernameAndIdIsNot(String username, Integer id);
}
