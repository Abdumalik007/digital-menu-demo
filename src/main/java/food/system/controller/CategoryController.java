package food.system.controller;


import food.system.service.main.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@CacheConfig(cacheNames = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@PathVariable String name) {
        return categoryService.createCategory(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestParam String name, @RequestParam Integer id) {
        return categoryService.updateCategory(name, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findCategoryById(@PathVariable Integer id) {
        return categoryService.findCategoryById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        return categoryService.deleteCategoryById(id);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchCategory(@PathVariable String name) {
        return categoryService.search(name);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Cacheable(value = "categoryCache", key = "'allCategories'", unless = "#withFood == false")
    public ResponseEntity<?> getAllCategories(@RequestParam boolean withFood) {
        return categoryService.getAllCategories(withFood);
    }

}
