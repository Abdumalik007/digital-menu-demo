package food.system.controller;


import food.system.dto.CategoryDto;
import food.system.service.main.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{name}")
    public ResponseEntity<?> createCategory(@PathVariable String name) {
        return categoryService.createCategory(name);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Integer id) {
        return categoryService.findCategoryById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        return categoryService.deleteCategoryById(id);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCategory(@PathVariable String name) {
        return categoryService.search(name);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
