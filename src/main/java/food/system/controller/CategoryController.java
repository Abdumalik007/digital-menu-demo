package food.system.controller;


import food.system.dto.CategoryDto;
import food.system.service.main.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

import static food.system.controller.StatisticController.USER_AMOUNT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@CacheConfig(cacheNames = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@PathVariable String name) {
        return categoryService.createCategory(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestParam String name, @RequestParam Integer id) {
        return categoryService.updateCategory(name, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable Integer id) {
        return categoryService.findCategoryById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        return categoryService.deleteCategoryById(id);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String name) {
        return categoryService.search(name);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get-all-with-food")
//    @Cacheable(key = "'allCategories'")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesWithFoods(HttpServletRequest request) {
        return categoryService.getAllCategoriesWithFood();
    }



}
