package food.system.repository;

import food.system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameAndIdIsNot(String name, Integer id);
    boolean existsByName(String name);
    List<Category> findAllByNameContainsIgnoreCase(String name);
}
