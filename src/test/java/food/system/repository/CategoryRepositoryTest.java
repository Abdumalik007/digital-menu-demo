package food.system.repository;

import food.system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryTest extends JpaRepository<Category, Integer> {
}
