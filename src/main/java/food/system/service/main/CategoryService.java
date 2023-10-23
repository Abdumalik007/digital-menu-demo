package food.system.service.main;

import food.system.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryDto> createCategory(String name);
    ResponseEntity<CategoryDto> updateCategory(String name, Integer id);
    ResponseEntity<CategoryDto> findCategoryById(Integer id);
    ResponseEntity<?> deleteCategoryById(Integer id);
    ResponseEntity<List<CategoryDto>> search(String name);
    ResponseEntity<List<CategoryDto>> getAllCategories();
    ResponseEntity<List<CategoryDto>> getAllCategoriesWithFood();
}
