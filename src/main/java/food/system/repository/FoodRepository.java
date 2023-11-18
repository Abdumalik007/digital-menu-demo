package food.system.repository;

import food.system.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findAllByNameAndCategory_Id(String name, Integer categoryId);
    List<Food> findAllByNameAndCategory_IdAndIdIsNot(String name, Integer categoryId, Integer id);
    List<Food> findAllByNameContainsIgnoreCase(String name);
    List<Food> findAllByCategoryIdOrderById(Integer id);
    List<Food> findAllByOrderById();
    List<Food> findAllByStatus(Boolean status);
}
