package food.system.controller;

import food.system.dto.FoodDto;
import food.system.service.main.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "Food")
public class FoodController {
    private final FoodService foodService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FoodDto> createFood(@ModelAttribute @Valid FoodDto foodDto,
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
    public ResponseEntity<FoodDto> findFoodById(@PathVariable Integer id) {
        return foodService.findFoodById(id);
    }
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<FoodDto>> getFoodByStatus(@PathVariable String status) {
        return foodService.findFoodByStatus(status);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFoodById(@PathVariable Integer id) {
        return foodService.deleteFoodById(id);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<FoodDto>> searchFood(@PathVariable String name) {
        return foodService.search(name);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        return foodService.getAllFoods();
    }


    @GetMapping("/category-id/{id}")
    public ResponseEntity<List<FoodDto>> findFoodByCategoryId(@PathVariable Integer id) {
        return foodService.findFoodByCategoryId(id);
    }

    @PutMapping("/food-status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WAITER')")
    public ResponseEntity<FoodDto> changeStatus(@RequestParam Integer id,
                                          @RequestParam boolean status) {
        return foodService.changeStatus(id, status);
    }

}
