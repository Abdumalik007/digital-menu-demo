package food.system.controller;

import food.system.dto.FoodDto;
import food.system.service.main.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    @PatchMapping("/food-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatus(@RequestParam(required = false) Integer id,
                                          @RequestParam(required = false) boolean status) {
        System.out.println(id + "  " + status);
        return foodService.changeStatus(id, status);
    }

    private final FoodService foodService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createFood(@ModelAttribute @Valid FoodDto foodDto, @RequestParam MultipartFile file) {
        return foodService.createFood(foodDto, file);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateFood(@ModelAttribute @Valid FoodDto foodDto, @RequestParam(required = false) MultipartFile file) {
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
    public ResponseEntity<?> getAllFoods() {
        return foodService.getAllFoods();
    }


    @GetMapping("/category-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findFoodByCategoryId(@PathVariable Integer id) {
        return foodService.findFoodByCategoryId(id);
    }


}
