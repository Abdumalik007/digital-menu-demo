package food.system.controller;

import food.system.dto.FoodDto;
import food.system.service.main.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "Food")
public class FoodController {
    private final FoodService foodService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createFood(@ModelAttribute @Valid FoodDto foodDto,
                                        @RequestParam MultipartFile file) {
        return foodService.createFood(foodDto, file);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object updateFood(@ModelAttribute @Valid FoodDto foodDto,
                                        @RequestParam(required = false) MultipartFile file) {
        return foodService.updateFood(foodDto, file);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findFoodById(@PathVariable Integer id) {
        return foodService.findFoodById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFoodById(@PathVariable Integer id) {
        return foodService.deleteFoodById(id);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchFood(@PathVariable String name) {
        return foodService.search(name);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Cacheable(key = "'allFoods'")
    public ResponseEntity<?> getAllFoods() {
        return foodService.getAllFoods();
    }


    @GetMapping("/category-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findFoodByCategoryId(@PathVariable Integer id) {
        return foodService.findFoodByCategoryId(id);
    }

    @PutMapping("/food-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatus(@RequestParam Integer id,
                                          @RequestParam boolean status) {
        return foodService.changeStatus(id, status);
    }

}
