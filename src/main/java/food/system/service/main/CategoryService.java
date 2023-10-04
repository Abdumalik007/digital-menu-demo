package food.system.service.main;

import food.system.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> createCategory(String name);
    ResponseEntity<?> updateCategory(CategoryDto categoryDto);
    ResponseEntity<?> findCategoryById(Integer id);
    ResponseEntity<?> deleteCategoryById(Integer id);
    ResponseEntity<?> search(String name);
    ResponseEntity<?> getAllCategories();
}
