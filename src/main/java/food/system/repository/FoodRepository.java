package food.system.repository;

import food.system.entity.Category;
import food.system.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    boolean existsByNameAndIdIsNot(String name, Integer id);
    boolean existsByName(String name);
    List<Food> findAllByNameContainsIgnoreCase(String name);
    List<Food> findAllByCategoryId(Integer id);
}
